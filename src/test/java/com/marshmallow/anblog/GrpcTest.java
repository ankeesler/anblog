package com.marshmallow.anblog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.marshmallow.anblog.generated.grpc.JWTSVID;
import com.marshmallow.anblog.generated.grpc.JWTSVIDRequest;
import com.marshmallow.anblog.generated.grpc.JWTSVIDResponse;
import com.marshmallow.anblog.generated.grpc.SpiffeWorkloadAPIGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.internal.GrpcUtil;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueDomainSocketChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerDomainSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GrpcTest {

  private static final String GOOD_JWT
          = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmtlZXNsZXIifQ.jy3szrdambIPNei84iAYxu"
          + "dg_9KICLoLUsoLTI-uztg";
  private static final String SOCKET_PATH = "/tmp/anblog_grpc_test.sock";

  private static class S extends SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIImplBase {
    public void fetchJWTSVID(com.marshmallow.anblog.generated.grpc.JWTSVIDRequest request,
                             io.grpc.stub.StreamObserver<com.marshmallow.anblog.generated.grpc.JWTSVIDResponse> responseObserver) {
      final JWTSVID svid = JWTSVID.newBuilder().setSvid(GOOD_JWT).build();
      final JWTSVIDResponse rsp = JWTSVIDResponse.newBuilder().addSvids(svid).build();
      responseObserver.onNext(rsp);
      responseObserver.onCompleted();
    }
  }

  private Server server;

  @Before
  public void setup() throws IOException {
    assertTrue(KQueue.isAvailable());
    server = NettyServerBuilder
            .forAddress(new DomainSocketAddress(SOCKET_PATH))
            .workerEventLoopGroup(new KQueueEventLoopGroup())
            .bossEventLoopGroup(new KQueueEventLoopGroup())
            .channelType(KQueueServerDomainSocketChannel.class)
            .addService(new S())
            .build();
    server.start();
  }
  
  @After
  public void teardown() throws InterruptedException {
    server.shutdown().awaitTermination();
  }

  @Test
  public void itWorks() {
    final ManagedChannel channel
            = NettyChannelBuilder.forAddress(new DomainSocketAddress(SOCKET_PATH))
            .eventLoopGroup(new KQueueEventLoopGroup())
            .channelType(KQueueDomainSocketChannel.class)
            .usePlaintext()
            .keepAliveTime(GrpcUtil.DEFAULT_KEEPALIVE_TIMEOUT_NANOS, TimeUnit.NANOSECONDS)
            .build();
    final SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIBlockingStub client
            = SpiffeWorkloadAPIGrpc.newBlockingStub(channel);
    final JWTSVIDResponse rsp = client.fetchJWTSVID(JWTSVIDRequest.newBuilder().build());
    assertEquals(1, rsp.getSvidsCount());
    assertEquals(GOOD_JWT, rsp.getSvids(0));
  }
}

package com.marshmallow.anblog;

import com.marshmallow.anblog.generated.grpc.SpiffeWorkloadAPIGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerDomainSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;

public class AuthClient {
  private final SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIBlockingStub client;

  public AuthClient(final String authSocket) {
    final ManagedChannel channel
            = NettyChannelBuilder.forAddress(new DomainSocketAddress(authSocket))
            .eventLoopGroup(new KQueueEventLoopGroup())
            .channelType(KQueueServerDomainSocketChannel.class)
            .usePlaintext()
            .build();
    client = SpiffeWorkloadAPIGrpc.newBlockingStub(channel);
  }
  
  
}

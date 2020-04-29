package com.marshmallow.anblog;

import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerDomainSocketChannel;
import com.marshmallow.anblog.generated.grpc.SpiffeWorkloadAPIGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {
  //@Value("${anblog.auth.socket}")
  private String authSocket = "/tmp/agent.sock";
  
  @Bean
  public TokenValidator tokenValidator() {
    final ManagedChannel channel
            = NettyChannelBuilder.forAddress(new DomainSocketAddress(authSocket))
            .eventLoopGroup(new KQueueEventLoopGroup())
            .channelType(KQueueServerDomainSocketChannel.class)
            .usePlaintext()
            .build();
    final SpiffeWorkloadAPIGrpc.SpiffeWorkloadAPIBlockingStub client
            = SpiffeWorkloadAPIGrpc.newBlockingStub(channel);
    return new SpiffeTokenValidator(client);
  }
  
}

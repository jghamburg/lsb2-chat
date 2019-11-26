package com.greglturnquist.learningspringboot.chat;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.netty.http.server.HttpServer;

// tag::code[]
@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
public class LearningSpringBootChatApplication {

  public static void main(String[] args) {
    SpringApplication.run(LearningSpringBootChatApplication.class, args);
  }

  @Bean @Primary
  public NettyReactiveWebServerFactory reactiveWebServerFactory() {
    NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
    webServerFactory.addServerCustomizers(new EventLoopNettyCustomizer());
    return webServerFactory;
  }

  private static class EventLoopNettyCustomizer implements NettyServerCustomizer {

    @Override
    public HttpServer apply(HttpServer httpServer) {
      EventLoopGroup parentGroup = new NioEventLoopGroup();
      EventLoopGroup childGroup = new NioEventLoopGroup();
      return httpServer.tcpConfiguration(
          tcpServer ->
              tcpServer.bootstrap(
                  serverBootstrap ->
                      serverBootstrap
                          .group(parentGroup, childGroup)
                          .channel(NioServerSocketChannel.class)));
    }
  }
}
// end::code[]

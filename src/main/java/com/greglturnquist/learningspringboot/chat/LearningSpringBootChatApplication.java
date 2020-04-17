package com.greglturnquist.learningspringboot.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

// tag::code[]
@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
public class LearningSpringBootChatApplication {

  public static void main(String[] args) {
    SpringApplication.run(LearningSpringBootChatApplication.class, args);
  }
}
// end::code[]

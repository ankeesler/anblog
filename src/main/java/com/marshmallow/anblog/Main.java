package com.marshmallow.anblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This main class is a stand-in for the generated org.openapitools.OpenAPI2SpringBoot main class.
 * That main class hardcodes the @ComponentScan basePackages which doesn't include anything in
 * the com.marshmallow.anblog tree :(.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "org.openapitools",
        "org.openapitools.api",
        "org.openapitools.configuration",
        "com.marshmallow.anblog",
})
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}

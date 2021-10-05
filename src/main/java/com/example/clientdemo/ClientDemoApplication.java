package com.example.clientdemo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@RestController
public class ClientDemoApplication {

    Logger logger = LoggerFactory.getLogger("ClientDemoApplication.class");

    @Autowired
    HelloProxy client;

    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class, args);
    }

    @GetMapping("/hello")
    @Retry(name= "hello-api", fallbackMethod = "fallbackHello")
//    @CircuitBreaker(name= "hello-api", , fallbackMethod =  "fallbackHello");
    public String hello(){
        logger.info("call received at hello endpoint");
        return "The hello service is returning: " + client.hello();
    }

    public String fallbackHello(Exception e){
        logger.info("call fallback method");
        return "This is a fallback hello";
    }
}

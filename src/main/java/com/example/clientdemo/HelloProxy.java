package com.example.clientdemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient("HELLO-SERVICE")
public interface HelloProxy {

    @GetMapping("/hello")
    String hello();
}

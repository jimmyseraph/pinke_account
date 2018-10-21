package com.pinke.liudao.pinke_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class PinkeAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinkeAccountApplication.class, args);
    }
}

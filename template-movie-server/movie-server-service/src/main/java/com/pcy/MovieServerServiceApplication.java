package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:31:55
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MovieServerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieServerServiceApplication.class, args);
    }
}

package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author PengChenyu
 * @since 2021-07-01 20:40:31
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GeneralUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralUserServiceApplication.class, args);
    }
}

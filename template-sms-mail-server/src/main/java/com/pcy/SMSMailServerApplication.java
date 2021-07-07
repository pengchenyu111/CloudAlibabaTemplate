package com.pcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 短信-邮件服务
 *
 * @author PengChenyu
 * @since 2021-07-07 16:09:35
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SMSMailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMSMailServerApplication.class, args);
    }
}

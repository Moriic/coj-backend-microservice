package com.cwc.cojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// startup.cmd -m standalone
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class CojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CojBackendGatewayApplication.class, args);
    }

}

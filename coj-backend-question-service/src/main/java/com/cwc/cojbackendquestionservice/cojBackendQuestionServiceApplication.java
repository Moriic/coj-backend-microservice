package com.cwc.cojbackendquestionservice;

import com.cwc.cojbackendserviceclient.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cwc.cojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.cwc")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cwc.cojbackendserviceclient.service"}, defaultConfiguration = DefaultFeignConfig.class)
public class cojBackendQuestionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(cojBackendQuestionServiceApplication.class, args);
    }
}

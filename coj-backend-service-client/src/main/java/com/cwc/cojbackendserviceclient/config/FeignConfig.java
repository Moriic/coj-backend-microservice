package com.cwc.cojbackendserviceclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class FeignConfig {

    @Bean
    public ObjectMapper feignObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 配置JavaTimeModule用于Java 8日期时间API的支持
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 设置LocalDateTime序列化和反序列化规则
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder(feignObjectMapper());
    }

    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(feignObjectMapper());
    }
}


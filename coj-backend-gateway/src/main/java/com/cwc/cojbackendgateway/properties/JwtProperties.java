package com.cwc.cojbackendgateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "coj.jwt")
@Data
public class JwtProperties {
    private String userSecretKey;
    private String userTokenName;
    private long tokenTtl;
    private long refreshTokenTtl;
}

package com.cwc.cojbackendgateway.filter;

import com.cwc.cojbackendcommon.constant.JwtClaimsConstant;
import com.cwc.cojbackendcommon.utils.JwtUtil;
import com.cwc.cojbackendgateway.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final JwtProperties jwtProperties;

    String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
            "/api", "/api-docs", "/api-docs/**", "/doc.html/**", "/v3/**", "/api/*/v2/api-docs",
            "/api/user/login", "/api/user/register",
            "/api/question/list/page/vo"};

    public GlobalAuthFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();
        // 判断路径中是否包含 inner，只允许内部调用
        if (antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限访问内部接口".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
        // JWT 校验，获取登录用户信息
        if (isExclude(path)) {
            return chain.filter(exchange);
        }
        List<String> headers = serverHttpRequest.getHeaders().get(jwtProperties.getUserTokenName());
        String token = null;
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }

        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            long userId = Long.parseLong(claims.get(JwtClaimsConstant.USER_ID).toString());
            String role = claims.get(JwtClaimsConstant.ROLE).toString();
            exchange.mutate()
                    .request(builder -> builder.header(JwtClaimsConstant.USER_ID, Long.toString(userId)))
                    .request(builder -> builder.header(JwtClaimsConstant.ROLE, role))
                    .build();

        } catch (Exception ex) {
            // 拦截
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("JWT校验失败".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }

        return chain.filter(exchange);
    }

    private boolean isExclude(String path) {
        for (String pathPattern : excludePatterns) {
            if (antPathMatcher.match(pathPattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 优先级提到最高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

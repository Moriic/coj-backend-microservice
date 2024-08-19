package com.cwc.cojbackendserviceclient.config;

import com.cwc.cojbackendcommon.constant.JwtClaimsConstant;
import com.cwc.cojbackendcommon.utils.BaseContext;
import com.cwc.cojbackendcommon.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level fullLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return requestTemplate -> {
            UserContext userContext = BaseContext.getCurrentUser();
            if(userContext != null){
                requestTemplate.header(JwtClaimsConstant.USER_ID, String.valueOf(userContext.getId()));
                requestTemplate.header(JwtClaimsConstant.ROLE, userContext.getRole());
            }
        };
    }

}

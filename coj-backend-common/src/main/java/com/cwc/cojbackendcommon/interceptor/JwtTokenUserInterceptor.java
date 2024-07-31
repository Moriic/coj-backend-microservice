package com.cwc.cojbackendcommon.interceptor;


import cn.hutool.core.util.StrUtil;
import com.cwc.cojbackendcommon.constant.JwtClaimsConstant;
import com.cwc.cojbackendcommon.utils.BaseContext;
import com.cwc.cojbackendcommon.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {


    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(JwtClaimsConstant.USER_ID);
        String role = request.getHeader(JwtClaimsConstant.ROLE);

        if (StrUtil.isNotBlank(userId) && StrUtil.isNotBlank(role)) {
            BaseContext.setCurrentUser(new UserContext(Long.valueOf(userId), role));
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentUser();
    }
}

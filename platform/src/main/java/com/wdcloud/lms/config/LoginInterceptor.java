package com.wdcloud.lms.config;

import com.wdcloud.lms.WebContext;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(100)
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!WebContext.isLogin()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String needLogin = Response.returnResponse(Code.ERROR, "need login");
            response.getOutputStream().write(needLogin.getBytes(StandardCharsets.UTF_8));
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}

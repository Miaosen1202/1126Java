package com.wdcloud.lms.config;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.core.base.model.OperationRecord;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@Order(110)
public class OperationLogFilter implements Filter {
    @Autowired
    private MqSendService mqSendService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("POST".equals(req.getMethod())) {
            BodyReaderRequestWrapper wrapper = new BodyReaderRequestWrapper(req);
            if(WebContext.isLogin()&&StringUtil.isNotEmpty(wrapper.getBody())){
                    OperationRecord operationRecord = OperationRecord.builder()
                            .body(wrapper.getBody())
                            .path(req.getRequestURI())
                            .userId(WebContext.getUserId()).build();
                    mqSendService.sendMessage(Constants.MQ_LOG, Constants.MQ_OPERATION_LOG, operationRecord);
            }
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

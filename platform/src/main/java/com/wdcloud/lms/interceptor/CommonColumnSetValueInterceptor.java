package com.wdcloud.lms.interceptor;

import com.wdcloud.lms.WebContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis拦截器: 拦截insert、update语句，createTime、updateTime是时间戳，由数据库管理
 * createUserId、updateUserId从工具类取值
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Component
public class CommonColumnSetValueInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
            if (invocation.getArgs().length > 1
                    && (statement.getSqlCommandType() == SqlCommandType.INSERT
                    || statement.getSqlCommandType() == SqlCommandType.UPDATE)) {
                log.debug("[CommonColumnSetValueInterceptor] set common column: createUserId, updateUserId, createTime, updateTime value");

                Object param = invocation.getArgs()[1];
               if(param instanceof Map&&((Map) param).containsKey("list")){
                  List list= (List) ((Map) param).get("list");
                   list.forEach(a->{
                       try {
                           dowork(statement, a);
                       } catch (InvocationTargetException e) {
                           e.printStackTrace();
                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                       }
                   });
               }else{
                   dowork(statement, param);
               }
            }
        } catch (Exception e) {
            log.error("[CommonColumnSetValueInterceptor] set sql userId error, msg={}", e.getMessage(), e);
        }

        return invocation.proceed();
    }

    private void dowork(MappedStatement statement, Object param) throws InvocationTargetException, IllegalAccessException {
        Class<?> paramClass = param.getClass();
        Method[] methods = paramClass.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();

            if (statement.getSqlCommandType() == SqlCommandType.INSERT
                    || statement.getSqlCommandType() == SqlCommandType.UPDATE) {
//                        if (methodName.equalsIgnoreCase("setCreateTime")
//                                || methodName.equalsIgnoreCase("setUpdateTime")) {
                if (methodName.equalsIgnoreCase("setUpdateTime")) {
                    method.invoke(param, (Date) null);
                }
            }

            Long userId = null;
            try {
                userId = WebContext.getUserId();
                if (userId == null) {
                    userId = 0L;
                }
            } catch (Exception e) {
                // do nothing
            }
            if (userId != null) {
                if (methodName.equalsIgnoreCase("setCreateUserId")) {
                    if (statement.getSqlCommandType() == SqlCommandType.INSERT) {
//                                Method getCreateUserId = paramClass.getMethod("getCreateUserId");
//                                Object createUserId = getCreateUserId.invoke(param);
//                                if (createUserId == null) {
//                                    method.invoke(param, userId);
//                                }
                        method.invoke(param, userId);
                    }
                }

                if (methodName.equalsIgnoreCase("setUpdateUserId")) {
                    if (statement.getSqlCommandType() == SqlCommandType.INSERT
                            || statement.getSqlCommandType() == SqlCommandType.UPDATE) {
//                                Method getUpdateUserId = paramClass.getMethod("getUpdateUserId");
//                                Object updateUserId = getUpdateUserId.invoke(param);
//                                if (updateUserId == null) {
//                                    method.invoke(param, userId);
//                                }
                        method.invoke(param, userId);
                    }
                }
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

package com.wdcloud.lms.config;

import com.wdcloud.lms.WebContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component("localeResolver")
public class LocaleMessageResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return WebContext.getUserLocale();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}

package com.wdcloud.lms;

import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Slf4j
public class WebContext {
    public static User getUser() {
        return (User) httpSession().getAttribute(Constants.SESSION_USER);
    }

    public static boolean isLogin() {
        return httpSession().getAttribute(Constants.SESSION_USER) != null;
    }

    // 获取用户区域
    public static Locale getUserLocale() {
        return Locale.forLanguageTag(getUserLanguage());
    }

    // 获取用户语言 默认英语 否则为用户设置语言
    public static String getUserLanguage() {
        if (httpSession().getAttribute(Constants.SESSION_USER_LANGUAGE) == null) {
            httpSession().setAttribute(Constants.SESSION_USER_LANGUAGE, Locale.ENGLISH.getLanguage());
        }
        return (String) httpSession().getAttribute(Constants.SESSION_USER_LANGUAGE);
    }

    public static Long getUserId() {
        try {
            return (Long) httpSession().getAttribute(Constants.SESSION_USER_ID);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Long getOrgId() {
        return (Long) httpSession().getAttribute(Constants.SESSION_USER_ORG_ID);
    }

    public static String getOrgTreeId() {
        return (String) httpSession().getAttribute(Constants.SESSION_USER_ORG_TREE_ID);
    }

    private static HttpSession httpSession() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
    }

    public static Long getRoleId() {
        return (Long) httpSession().getAttribute(Constants.SESSION_USER_ROLE_ID);
    }

    public static boolean isStudent() {
        return RoleEnum.STUDENT.getType().equals(getRoleId());
    }

}

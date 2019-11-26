package com.wdcloud.lms.core.base.mapper.ext;

import org.apache.ibatis.annotations.Param;

/**
 * @author 黄建林
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface UserEmailExtMapper {

    /**
     * 功能：用户关联邮件是否存在
     * @param email
     * @return
     */
    int getEmailIsExist(@Param("email") String email);


}

package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxutao
 */
@Data
public class StudentDataListVo {
    /**
     * //学生id
     */
    private Long id =0L;
    /**
     * //学生id
     */
    private Long userId =0L ;
    /**
     * //小组0 学生1
     */
    private Integer type;
    private String nickName;
    /**
     * //学生名称
     */
    private String studentName;
    /**
     * // 学生头像
     */
    private String studentFile;
    /**
     * //小组名称
     */
    private String groupName;
    /**
     * fullName
     */
    private String fullName;

}

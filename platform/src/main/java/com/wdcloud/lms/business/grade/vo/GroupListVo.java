package com.wdcloud.lms.business.grade.vo;


import lombok.Data;

/**
 * @author zhangxutao
 */
@Data
public class GroupListVo {
    /**
     * //小组ID
     */
    private Long Id=0L ;
    /**
     * //小组名称
     */
    private String groupName;
    /**
     * //小组0 学生1
     */
    private Integer type;
    /**
     * //学生id
     */
    private Long userId=0L ;
    private String nickName;
    /**
     * First Name + Last Name
     */
    private String fullName;
    private String studentName;
    private String studentFile;

}

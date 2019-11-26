package com.wdcloud.lms.base.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignUserOparateDTO implements Serializable {
    /**
     * 操作类型：1:Add;2:delete;3:Move(从A班级移动到B班级）
     */
    private Integer oparateType;
    /**
     * 所属课程id
     */
    private Long courseId;
    /**
     * 操作的用户id
     */
    private Long userId;
    /**
     * 操作用户的班级id
     */
    private Long srcSectionId;
    /**
     * 移动时的目的班级id，只有oparateType=3时，需要传值
     */
    private Long destSectionId;


    @Getter
    @AllArgsConstructor
    public enum OperateType {
        ADD(1),
        DELETE(2),
        MOVE(3);

        private Integer type;
    }
}

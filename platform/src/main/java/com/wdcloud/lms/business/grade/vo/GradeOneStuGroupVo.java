package com.wdcloud.lms.business.grade.vo;

import com.wdcloud.lms.core.base.vo.StudentDataListVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxutao
 */
@Data
public class GradeOneStuGroupVo {
    /**
     * //小组LIST
     */
    private List<GroupListVo> groupListVoList = new ArrayList<>();
    /**
     * //学生LIST
     */
    private List<StudentDataListVo> studentListVos = new ArrayList<>();
}

package com.wdcloud.lms.business.grade.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import com.wdcloud.lms.business.grade.vo.GroupListVo;
import com.wdcloud.lms.business.grade.vo.GradeInfoVo;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

/**
 * @author zhangxutao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeQueryVo  {
    /**
     * //评分的基本信息
     */
    private GradeInfoVo gradeInfoVo;

    /**
     * //作业类型内容信息
     */
    private GradeOneBasicsInfoVo gradeOneBasicsInfoVo;


}

package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.GradeSchemeDao;
import com.wdcloud.lms.core.base.dao.GradeSchemeItemDao;
import com.wdcloud.lms.core.base.model.GradeScheme;
import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import com.wdcloud.lms.core.base.vo.TodoVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.GRADE_SCHEME)
public class GradeSchemeDataQuery implements IDataQueryComponent<List<GradeSchemeItem>> {
    @Autowired
    private GradeSchemeDao gradeSchemeDao;
    @Autowired
    private GradeSchemeItemDao gradeSchemeItemDao;

    /**
     * @api {get} /gradeScheme/get 评分模板详情
     * @apiName gradeSchemeGet
     * @apiGroup Grade2.0
     *
     * @apiParam {Number} data 课程Id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     */
    @Override
    public List<GradeSchemeItem> find(String id) {
        Long courseId = Long.valueOf(id);
        GradeScheme gradeScheme=gradeSchemeDao.findOne(GradeScheme.builder().courseId(courseId).build());
        if (gradeScheme != null) {
            List<GradeSchemeItem> gradeSchemeItemList=gradeSchemeItemDao.find(GradeSchemeItem.builder().gradeSchemeId(gradeScheme.getId()).build());
            return gradeSchemeItemList.stream().sorted(Comparator.comparing(GradeSchemeItem::getRangeStart).reversed()).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }
}

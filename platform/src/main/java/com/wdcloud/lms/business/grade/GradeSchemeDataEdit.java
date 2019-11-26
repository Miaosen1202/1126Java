package com.wdcloud.lms.business.grade;


import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.dto.GradeSchemeDTO;
import com.wdcloud.lms.core.base.dao.GradeSchemeDao;
import com.wdcloud.lms.core.base.dao.GradeSchemeItemDao;
import com.wdcloud.lms.core.base.model.GradeScheme;
import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 评分模板 增加
 * @author wangff
 **/
@ResourceInfo(name = Constants.GRADE_SCHEME)
public class GradeSchemeDataEdit implements IDataEditComponent {

    @Autowired
    private GradeSchemeDao gradeSchemeDao;
    @Autowired
    private GradeSchemeItemDao gradeSchemeItemDao;
    /**
     * @api {post} /gradeScheme/add 添加评分模板
     * @apiDescription 添加评分模板
     * @apiName gradeSchemeAdd
     * @apiGroup Grade2.0
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Object[]} schemeItems 模板
     * @apiParam {String} schemeItems.code 字母
     * @apiParam {Number} schemeItems.rangeStart 百分比下限，最小值0
     * @apiParam {Number} schemeItems.rangeEnd   百分比上限，最大值100.00
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @ValidationParam(clazz = GradeSchemeDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        GradeSchemeDTO dto = JSON.parseObject(dataEditInfo.beanJson, GradeSchemeDTO.class);
        //1 添加模板主表（已存在则忽略）
        GradeScheme gradeScheme=gradeSchemeDao.findOne(GradeScheme.builder().courseId(dto.getCourseId()).build());
        if (gradeScheme == null) {
            gradeScheme = GradeScheme.builder().courseId(dto.getCourseId()).name("").build();
            gradeSchemeDao.save(gradeScheme);
        }
        //2 批量添加模板明细（先删除，后添加）
        gradeSchemeItemDao.deleteByField(GradeSchemeItem.builder().gradeSchemeId(gradeScheme.getId()).build());
        GradeScheme finalGradeScheme = gradeScheme;
        dto.getSchemeItems().forEach(gradeSchemeItem -> {
            gradeSchemeItem.setGradeSchemeId(finalGradeScheme.getId());
        });
        if (ListUtils.isNotEmpty(dto.getSchemeItems())) {
            gradeSchemeItemDao.batchInsert(dto.getSchemeItems());
        }
        return new LinkedInfo(dto.getCourseId().toString());
    }

}
package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE_USER,
        functionName = Constants.FUNCTION_TYPE_SECTION_REMOVE
)
public class CourseUserRemoveSectionEdit implements ISelfDefinedEdit {

    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /courseUser/sectionRemove/edit
     * @apiDescription 移除班级用户,用户必须至少保留一个班级
     * @apiName courseUserSectionRemove
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     * @apiParam {Number} sectionId 班级
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 用户ID
     *
     */
    @ValidationParam(clazz = CourseUserRemoveSectionVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseUserRemoveSectionVo courseUserRemoveSectionVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserRemoveSectionVo.class);

        Example example = sectionUserDao.getExample();
        Long courseId = courseUserRemoveSectionVo.getCourseId();
        Long userId = courseUserRemoveSectionVo.getUserId();
        for (Long sectionId : courseUserRemoveSectionVo.getSectionIds()) {
            sectionUserService.delete(courseId, sectionId, userId, null);
        }

        if (sectionUserDao.count(SectionUser.builder().courseId(courseId).userId(userId).build()) == 0) {
            throw new BaseException("user.cannot.remove.all.section");
        }
        return new LinkedInfo(String.valueOf(courseUserRemoveSectionVo.userId));
    }

    @Data
    public static class CourseUserRemoveSectionVo {
        @NotNull
        private Long courseId;
        @NotNull
        private Long userId;
        @NotEmpty
        private List<Long> sectionIds;
    }
}

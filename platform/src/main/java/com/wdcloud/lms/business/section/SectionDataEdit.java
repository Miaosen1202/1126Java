package com.wdcloud.lms.business.section;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.section.vo.SectionEditVo;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_SECTION)
public class SectionDataEdit implements IDataEditComponent {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private RoleService roleService;

    /**
     * @@api {post} /section/add 班级添加
     * @apiName SectionAdd
     * @apiGroup Section
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} name 班级名称
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 班级ID
     */
    @ValidationParam(clazz = SectionEditVo.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        Section section = JSON.parseObject(dataEditInfo.beanJson, Section.class);
        if (!roleService.hasTeacherOrTutorRole()) {
            throw new PermissionException();
        }

        sectionDao.save(section);
        return new LinkedInfo(String.valueOf(section.getId()));
    }

    /**
     * @@api {post} /section/modify 班级修改
     * @apiName SectionModify
     * @apiGroup Section
     *
     * @apiParam {Number} id 班级ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} name 班级名称
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 班级ID
     */
    @ValidationParam(clazz = SectionEditVo.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        Section section = JSON.parseObject(dataEditInfo.beanJson, Section.class);
        Section oldSection = sectionDao.get(section.getId());
        if (oldSection == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_ID, section.getId());
        }

        oldSection.setName(section.getName());
        oldSection.setStartTime(section.getStartTime());
        oldSection.setEndTime(section.getEndTime());
        sectionDao.updateIncludeNull(oldSection);
        return new LinkedInfo(String.valueOf(section.getId()));
    }

    /**
     * @@api {post} /section/deletes 班级删除
     * @apiName SectionDelete
     * @apiGroup Section
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} name 班级名称
     * @apiParam {Number} [startTime] 开始时间
     * @apiParam {Number} [endTime] 结束时间
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 班级ID
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        sectionDao.deletes(ids);
        return new LinkedInfo(dataEditInfo.beanJson);
    }
}

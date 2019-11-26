package com.wdcloud.lms.business.term;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.term.dto.TermDTO;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.TermConfigDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.model.TermConfig;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@ResourceInfo(name = Constants.RESOURCE_TYPE_TERM)
public class TermDataEdit implements IDataEditComponent {
    @Autowired
    private TermDao termDao;
    @Autowired
    private TermConfigDao termConfigDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {post} /term/add 学期添加
     * @apiDescription 学期添加
     * @apiName termAdd
     * @apiGroup Term
     * @apiParam {String{..50}} name 学期名称
     * @apiParam {String} [sisId] 学期SIS ID
     * @apiParam {Number} [termStartTime] 学期开始时间(使用毫秒值）
     * @apiParam {Number} [termEndTime] 学期截止时间(使用毫秒值）
     * @apiParam {Number} [studentStartTime] 学生访问生效时间(使用毫秒值）
     * @apiParam {Number} [studentEndTime] 学生访问截止时间(使用毫秒值）
     * @apiParam {Number} [teacherStartTime] 教师访问生效时间(使用毫秒值）
     * @apiParam {Number} [teacherEndTime] 教师访问截止时间(使用毫秒值）
     * @apiParam {Number} [taStartTime] 助教访问生效时间(使用毫秒值）
     * @apiParam {Number} [taEndTime] 助教访问截止时间(使用毫秒值）
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "1",
     * "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    @ValidationParam(clazz = TermDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return new LinkedInfo(Code.ERROR.name);
        }
        TermDTO dto = JSON.parseObject(dataEditInfo.beanJson, TermDTO.class);
        //保存学期
        Term term = Term.builder()
                .name(dto.getName())
                .isDefault(0)
                .orgId(WebContext.getOrgId())
                .orgTreeId(WebContext.getOrgTreeId())
                .code(UUID.randomUUID().toString())
                .sisId(dto.getSisId())
                .startTime(dto.getTermStartTime())
                .endTime(dto.getTermEndTime())
                .build();
        termDao.insert(term);
        //保存教师 助教 学生 时间
        insertTermConfig(term.getId(), roleService.getTeacherRoleId(), dto.getTeacherStartTime(), dto.getTeacherEndTime());
        insertTermConfig(term.getId(), roleService.getTaRoleId(), dto.getTaStartTime(), dto.getTaEndTime());
        insertTermConfig(term.getId(), roleService.getStudentRoleId(), dto.getStudentStartTime(), dto.getStudentEndTime());
        return new LinkedInfo(Code.OK.name);
    }

    private void insertTermConfig(Long termId, Long aLong, Date startTime, Date endTime) {
        termConfigDao.insert(TermConfig.builder()
                .termId(termId)
                .roleId(aLong)
                .startTime(startTime)
                .endTime(endTime)
                .build());
    }


    /**
     * @api {post} /term/modify 学期修改
     * @apiDescription 学期修改
     * @apiName termModify
     * @apiGroup Term
     * @apiParam {String} id 学期id
     * @apiParam {String{..50}} name 学期名称
     * @apiParam {String} [sisId] 学期SIS ID
     * @apiParam {Number} [termStartTime] 学期开始时间(使用毫秒值）
     * @apiParam {Number} [termEndTime] 学期截止时间(使用毫秒值）
     * @apiParam {Number} [studentStartTime] 学生访问生效时间(使用毫秒值）
     * @apiParam {Number} [studentEndTime] 学生访问截止时间(使用毫秒值）
     * @apiParam {Number} [teacherStartTime] 教师访问生效时间(使用毫秒值）
     * @apiParam {Number} [teacherEndTime] 教师访问截止时间(使用毫秒值）
     * @apiParam {Number} [taStartTime] 助教访问生效时间(使用毫秒值）
     * @apiParam {Number} [taEndTime] 助教访问截止时间(使用毫秒值）
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "1",
     * "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    @ValidationParam(clazz = TermDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return new LinkedInfo(Code.ERROR.name);
        }
        TermDTO dto = JSON.parseObject(dataEditInfo.beanJson, TermDTO.class);
        //保存学期
        Term term = termDao.get(dto.getId());
        term.setName(dto.getName());
        term.setSisId(dto.getSisId());
        term.setStartTime(dto.getTermStartTime());
        term.setEndTime(dto.getTermEndTime());
        termDao.updateIncludeNull(term);
        //删除所有
        termConfigDao.deleteByField(TermConfig.builder().termId(dto.getId()).build());
        //保存教师 助教 学生 时间
        insertTermConfig(term.getId(), roleService.getTeacherRoleId(), dto.getTeacherStartTime(), dto.getTeacherEndTime());
        insertTermConfig(term.getId(), roleService.getTaRoleId(), dto.getTaStartTime(), dto.getTaEndTime());
        insertTermConfig(term.getId(), roleService.getStudentRoleId(), dto.getStudentStartTime(), dto.getStudentEndTime());
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /term/deletes 学期删除
     * @apiDescription 学期删除
     * @apiName termDeletes
     * @apiGroup Term
     * @apiParam  {Number} id 学期ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return new LinkedInfo(Code.ERROR.name);
        }
        Term termOrig = JSON.parseObject(dataEditInfo.beanJson, Term.class);
        Long id = termOrig.getId();
        Term term = termDao.get(id);
        if (Objects.equals(term.getIsDefault(), Status.YES.getStatus())) {
            throw new PermissionException();
        }

        //删除时看学期下面是否有课程
        if (courseDao.count(Course.builder().termId(id).build())>0) {
            return new LinkedInfo(Code.ERROR.name);
        }

        termDao.delete(id);
        termConfigDao.deleteByField(TermConfig.builder().termId(id).build());
        return new LinkedInfo(Code.OK.name);
    }
}

package com.wdcloud.lms.business.announce;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.ContentViewRecordService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.announce.dto.AnnounceAddDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.AnnounceDao;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_ANNOUNCE)
public class AnnounceDataEdit implements IDataEditComponent {

    @Autowired
    private AnnounceDao announceDao;
    @Autowired
    private AssignService assignService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private ContentViewRecordService contentViewRecordService;

    /**
     * @api {post} /announce/add 公告添加
     * @apiName announceAdd公告添加
     * @apiGroup Announce
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} topic 标题
     * @apiParam {String} content 内容
     * @apiParam {Object[]} [assign]分配
     * @apiParam {Number=1,2} [assign.assignType] 分配类型，1: 所有， 2：section(班级)
     * @apiParam {Number} [assign.assignId]  分配目标ID，assignType=2时为班级ID
     * @apiParam {String} [fileId] 文件id
     * @apiParam {Number} [publishTime] 发布时间
     * @apiParam {Number=0,1} allowComment 是否允许评论
     * @apiParam {Number} [studyGroupId] 小组ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = AnnounceAddDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        AnnounceAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, AnnounceAddDTO.class);
        //学生只能创建小组公告。
        boolean studentRole = roleService.hasStudentRole();
        if (studentRole && Objects.isNull(dto.getStudyGroupId())) {
            log.info("学生只能创建小组公告！");
            throw new PermissionException();
        }
        //0附件上传
        saveFile(dto);
        if (Objects.isNull(dto.getPublishTime())) {
            dto.setPublishTime(new Date());
        }
        //1公告基础表添加
        announceDao.save(dto);
        //2分配保存
        saveAssign(dto);
        //插入已读记录
        contentViewRecordService.insert(dto.getId(), ContentViewRecordOrignTypeEnum.ANNOUNCE_TOPIC.getType());
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    private void saveAssign(AnnounceAddDTO dto) {
        //课程公告的分配不能为空
        if (Objects.isNull(dto.getStudyGroupId()) && ListUtils.isEmpty(dto.getAssign())) {
            log.info("课程公告的分配不能为空！");
            throw new ParamErrorException();
        }
        //2课程公告 关联表 分配
        if (ListUtils.isNotEmpty(dto.getAssign())) {
            assignService.batchSave(dto.getAssign(), dto.getCourseId(), OriginTypeEnum.ANNOUNCE, dto.getId());
        }
    }


    /**
     * @api {post} /announce/modify 公告修改
     * @apiName announceModify
     * @apiGroup Announce
     * @apiParam {Number} id 公告ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} topic 标题
     * @apiParam {String} content 内容
     * @apiParam {Number} [studyGroupId] 小组公告，小组ID
     * @apiParam {Object[]} [assign]分配
     * @apiParam {Number=1,2} [assign.assignType] 分配类型，1: 所有， 2：section(班级)
     * @apiParam {Number} [assign.assignId]  分配目标ID，assignType=2时为班级ID
     * @apiParam {Number} [attachmentFileId] 附件id
     * @apiParam {String} [fileId]  文件ID
     * @apiParam {Number} [publishTime] 发布时间
     * @apiParam {Number=0,1} allowComment 是否允许评论
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 公告ID
     */
    @Override
    @ValidationParam(clazz = AnnounceAddDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        AnnounceAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, AnnounceAddDTO.class);
        if (Objects.isNull(announceDao.findOne(Announce.builder().id(dto.getId()).build()))) {
            throw new BaseException("prop.value.not-exists", "announce", dto.getId() + "");
        }
        //学生只能创建小组公告。
        boolean studentRole = roleService.hasStudentRole();
        if (studentRole && Objects.isNull(dto.getStudyGroupId())) {
            log.info("学生只能操作小组公告！");
            throw new PermissionException();
        }
        //附件上传
        saveFile(dto);
        if (Objects.isNull(dto.getPublishTime())) {
            dto.setPublishTime(new Date());
        }
        //公告基础表更新
        announceDao.update(dto);
        //分配保存
        saveAssign(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    private void saveFile(AnnounceAddDTO dto) {
        if (StringUtils.isNotBlank(dto.getFileId())) {
            UserFile userFile = null;
            if (Objects.isNull(dto.getStudyGroupId())) {//课程公告文件
                userFile=userFileService.saveCourseContentAttachment(userFileService.getFileInfo(dto.getFileId()), dto.getCourseId());
            } else {
                userFile=userFileService.saveStudyGroupContentAttachment(userFileService.getFileInfo(dto.getFileId()), dto.getCourseId(), dto.getStudyGroupId());
            }
            dto.setAttachmentFileId(userFile.getId());
        }
    }

    /**
     * @api {post} /announce/deletes 公告删除
     * @apiName announceDeletes
     * @apiGroup Announce
     * @apiParam {Number[]} ids 公告ID列表
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] ID列表
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        Announce announce = Announce.builder().isDeleted(Status.YES.getStatus()).build();
        int count = announceDao.updateByExample(announce, idList);
        return new LinkedInfo(String.valueOf(count));
    }
}

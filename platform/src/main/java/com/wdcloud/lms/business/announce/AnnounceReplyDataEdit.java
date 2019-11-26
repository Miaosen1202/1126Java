package com.wdcloud.lms.business.announce;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.UserParticipateOpEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.ContentViewRecordService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.announce.dto.AnnounceReplyAddDTO;
import com.wdcloud.lms.core.base.dao.AnnounceDao;
import com.wdcloud.lms.core.base.dao.AnnounceReplyDao;
import com.wdcloud.lms.core.base.dao.UserParticipateDao;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_ANNOUNCE_REPLY)
public class AnnounceReplyDataEdit implements IDataEditComponent {
    @Autowired
    private AnnounceReplyDao announceReplyDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private ContentViewRecordService contentViewRecordService;
    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private AnnounceDao announceDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    /**
     * @api {post} /announceReply/add 公告回复添加
     * @apiName announceReplyAdd
     * @apiGroup Announce
     * @apiParam {Number} announceId 公告ID
     * @apiParam {Number} [studyGroupId] 学习小组ID
     * @apiParam {Number} [announceReplyId] 回复ID
     * @apiParam {String} content 内容
     * @apiParam {Number} [fileId] 文件ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增回复ID
     */
    @Override
    @ValidationParam(clazz = AnnounceReplyAddDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        AnnounceReplyAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, AnnounceReplyAddDTO.class);
        //学生发评论校验
        verifyPermission(dto.getAnnounceId());

        if (Objects.isNull(dto.getAnnounceReplyId())) {
            dto.setAnnounceReplyId((long) 0);
        }
        //附件上传
        saveFile(dto);
        announceReplyDao.save(dto);
        contentViewRecordService.insert(dto.getId(), ContentViewRecordOrignTypeEnum.ANNOUNCE_REPLY.getType());
        saveUserParticipate(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    private void verifyPermission(Long announceId) {
        if (roleService.hasStudentRole()) {
            Announce announce=announceDao.findOne(Announce.builder().id(announceId).build());
            if (announce!= null) {
                if(Status.NO.getStatus()==announce.getAllowComment().intValue()){
                    throw new PermissionException();
                }
                //时间未到或者已过 不能操作
                AssignUser assignUser=assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(),OriginTypeEnum.ANNOUNCE.getType(),announceId);
                if (assignUser != null) {
                    if (assignUser.getStartTime()!=null&&assignUser.getStartTime().after(new Date())) {
                        throw new PermissionException();
                    }
                    if (assignUser.getEndTime()!=null&&assignUser.getEndTime().before(new Date())) {
                        throw new PermissionException();
                    }
                }
            }

        }
    }

    private void saveUserParticipate(AnnounceReplyAddDTO dto) {
        Announce announce=announceDao.findOne(Announce.builder().id(dto.getAnnounceId()).build());
        UserParticipate have=userParticipateDao.findOne(UserParticipate.builder()
                .courseId(announce.getCourseId()).originType(OriginTypeEnum.ANNOUNCE.getType()).originId(announce.getId())
                .userId(WebContext.getUserId()).build());
        if (have == null) {
            UserParticipate userParticipate = UserParticipate.builder()
                    .courseId(announce.getCourseId())
                    .originType(OriginTypeEnum.ANNOUNCE.getType())
                    .originId(announce.getId())
                    .operation(UserParticipateOpEnum.SUBMIT.getOp())
                    .userId(WebContext.getUserId())
                    .targetName(announce.getTopic())
                    .build();
            userParticipateDao.save(userParticipate);
        }
    }

    private void saveFile(AnnounceReplyAddDTO dto) {
       if (StringUtils.isNotBlank(dto.getFileId())) {
            UserFile userFile = userFileService.saveReplyAttachment((userFileService.getFileInfo(dto.getFileId())));
            dto.setAttachmentFileId(userFile.getId());
        }
    }

    /**
     * @api {post} /announceReply/modify 公告回复修改
     * @apiName announceReplyModify
     * @apiGroup Announce
     * @apiParam {Number} id 回复ID
     * @apiParam {String} content 内容
     * @apiParam {Number} [attachmentFileId] 附件主键Id
     * @apiParam {String} [fileId] 附件Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 回复ID
     */
    @Override
    @ValidationParam(clazz = AnnounceReplyAddDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        AnnounceReplyAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, AnnounceReplyAddDTO.class);
        //学生发评论校验
        AnnounceReply reply=announceReplyDao.findOne(AnnounceReply.builder().id(dto.getId()).build());
        verifyPermission(reply.getAnnounceId());
        //附件上传
        saveFile(dto);
        announceReplyDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /announceReply/deletes 公告回复刪除
     * @apiName announceReplyDelete
     * @apiGroup Announce
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample {json} 请求示例：
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除ID列表
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        if (ListUtils.isEmpty(idList)) {
            throw new ParamErrorException();
        }
        //权限校验
        AnnounceReply announceReply=announceReplyDao.findOne(AnnounceReply.builder().id(idList.get(0)).build());
        verifyPermission(announceReply.getAnnounceId());

        AnnounceReply reply = AnnounceReply.builder().isDeleted(Status.YES.getStatus()).build();
        int count = announceReplyDao.updateByExample(reply, idList);
        idList.forEach(id->{
            List<AnnounceReply> children=announceReplyDao.find(AnnounceReply.builder().announceReplyId(id).build());
            if (ListUtils.isNotEmpty(children)) {
                List<Long> childrenIdList=children.stream().map(AnnounceReply::getId).collect(Collectors.toList());
                announceReplyDao.updateByExample(reply, childrenIdList);
            }
        });
        return new LinkedInfo(count + "");
    }
}

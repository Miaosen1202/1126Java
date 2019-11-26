package com.wdcloud.lms.business.announce;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.announce.enums.IsReadTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.AnnounceVo;
import com.wdcloud.lms.core.base.vo.ReadCountDTO;
import com.wdcloud.lms.core.base.vo.SectionVo;
import com.wdcloud.lms.core.base.vo.UserVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_ANNOUNCE)
public class AnnounceDataQuery implements IDataQueryComponent<Announce> {

    @Autowired
    private AnnounceDao announceDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ContentViewRecordDao contentViewRecordDao;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /announce/list 公告列表
     * @apiName announceList
     * @apiGroup Announce
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:所有
     * @apiParam {String} topic 公告标题
     * @apiParam {Number} [studyGroupId] 学习小组Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity列表
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {Number} [entity.studyGroupId] 小组ID
     * @apiSuccess {String} entity.topic 标题
     * @apiSuccess {String} [entity.content] 内容
     * @apiSuccess {Number} entity.publishTime 发布时间
     * @apiSuccess {Number=0,1} entity.isRead 是否已读
     * @apiSuccess {Number=0,1} entity.allowComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number} [entity.attachmentFileId] 附件ID
     * @apiSuccess {Object} [entity.readCountDTO] 评论数统计节点
     * @apiSuccess {Number} [entity.readCountDTO.replyTotal] 总评论数
     * @apiSuccess {Number} [entity.readCountDTO.replyNotReadTotal] 评论未读数
     * @apiSuccess {Number} [entity.createUserNickname] 创建者昵称
     * @apiSuccess {Number} [entity.createUserAvatar] 创建者头像
     */
    @Override
    public List<? extends Announce> list(Map<String, String> param) {
        param.put("userId", WebContext.getUserId() + "");
        Long studyGroupId = StringUtils.isBlank(param.get("studyGroupId"))?null:Long.valueOf(param.get("studyGroupId"));
        //公告列表，分配,评论数
        List<AnnounceVo> announceVoList = findAnnounceList(param);

        if (ListUtils.isNotEmpty(announceVoList)) {
            //公告列表下每条公告评论的评论数统计
            List<Long> announceIds = announceVoList.stream().map(AnnounceVo::getId).collect(Collectors.toList());
            List<ReadCountDTO> unReadlist = contentViewRecordDao.findAnnounceOrDiscussionReplyUnreadTotal(studyGroupId, announceIds, WebContext.getUserId(), OriginTypeEnum.ANNOUNCE);
            Map<Long, ReadCountDTO> unReadAnnounceMap = unReadlist.stream().collect(Collectors.toMap(ReadCountDTO::getOriginId, a -> a));
            announceVoList.forEach(announceVo -> {
                announceVo.setReadCountDTO(unReadAnnounceMap.get(announceVo.getId()));
            });
            //未读过滤
            if (IsReadTypeEnum.NOTREAD.getType().equals(Integer.valueOf(param.get("isRead")))) {
                announceVoList = announceVoList.stream()
                        .filter(announceVo ->(announceVo.getReadCountDTO() != null && announceVo.getReadCountDTO().getReplyNotReadTotal() > 0))
                        .collect(Collectors.toList());
            }
        }
        //列表为空或者小组公告直接返回
        if (ListUtils.isEmpty(announceVoList) || Objects.nonNull(param.get("studyGroupId"))) {
            return announceVoList;
        } else {
            //列表非空并且是课程公告带出 会话节点
            buildSections(param,announceVoList);
            return announceVoList;
        }
    }

    /**
     * 列表非空并且是课程公告带出 会话节点
     * @param param
     * @param announceVoList
     */
    private void buildSections(Map<String, String> param, List<AnnounceVo> announceVoList) {
        List<SectionVo> sectionList = sectionDao.findSectionListByCourseId(param);
        Map<Long, SectionVo> sectionMap = sectionList.stream().collect(Collectors.toMap(SectionVo::getSectionId, a -> a));
        Long userCount = sectionList.stream().map(SectionVo::getUserCount).reduce(Long::sum).get();
        announceVoList.forEach(announceVo -> {
            announceVo.getAssign().forEach(assign -> {
                if (assign.getAssignType().equals(AssignTypeEnum.ALL.getType())) {
                    announceVo.setUserCount(userCount);
                } else if (assign.getAssignType().equals(AssignTypeEnum.SECTION.getType())) {
                    announceVo.getSectionList().add(sectionMap.get(assign.getAssignId()));
                }
            });
        });
    }

    private List<AnnounceVo> findAnnounceList(Map<String, String> param) {
        List<AnnounceVo> announceVoList = null;
        if (!roleService.hasStudentRole()) {
            announceVoList = announceDao.searchByTeacher(param);
        } else {//学生
            if (Objects.isNull(param.get("studyGroupId"))) { //课程公告列表 分配所有或者我所在班级的已发布公告且小组ID为空
                announceVoList = announceDao.searchCourseAnnounceByStudent(param);
            } else {//学生小组公告列表：studyGroupId 下所有有效公告
                announceVoList = announceDao.searchGroupAnnounceByStudent(param);
            }
        }
        return announceVoList;
    }


    /**
     * @api {get} /announce/pageList 公告列表分页
     * @apiName announcePageList
     * @apiGroup Announce
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1,2} isRead 是否已读 0:默认所有,1:已读，2:未读
     * @apiParam {String} topic 公告标题
     * @apiParam {Number} [studyGroupId] 学习小组Id
     * @apiParam {Number} [pageIndex] 页码
     * @apiParam {Number} [pageSize] 页大小
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 列表
     * @apiSuccess {Number} entity.list.id ID
     * @apiSuccess {Number} entity.list.courseId 课程ID
     * @apiSuccess {Number} [entity.list.studyGroupId] 小组ID
     * @apiSuccess {String} entity.list.topic 标题
     * @apiSuccess {String} [entity.list.content] 内容
     * @apiSuccess {Number} entity.list.publishTime 发布时间
     * @apiSuccess {Number=0,1} entity.list.isRead 是否已读
     * @apiSuccess {Number=0,1} entity.list.allowComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number} [entity.list.attachmentFileId] 附件ID
     */
    @Override
    public PageQueryResult<? extends Announce> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }


    /**
     * @api {get} /announce/get 公告详情
     * @apiName announceGet
     * @apiGroup Announce
     * @apiParam {Number} data 公告Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.topic 标题
     * @apiSuccess {String} [entity.content] 标题
     * @apiSuccess {Number} [entity.studyGroupSetId] 学习小组集ID
     * @apiSuccess {Number=0,1} [entity.allowComment] 允许评论
     * @apiSuccess {Number} entity.publishTime 发布时间
     * @apiSuccess {Number=0,1} entity.allowComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number} [entity.attachmentFileId] 附件ID
     * @apiSuccess {Object} [entity.attachmentFile] 讨论话题附件
     * @apiSuccess {String} [entity.attachmentFile.fileName] 附件名称
     * @apiSuccess {String} [entity.attachmentFile.fileUrl] 附件url
     * @apiSuccess {Object[]} entity.assign 分配数组
     * @apiSuccess {Number=1,2,3} entity.assign.assignType 分配类型，1: 所有， 2：section(班级) 3用户
     * @apiSuccess {Number} entity.ssign.assignId]  分配目标ID 班级Id或者用户Id，分配类型为所有时为空
     * @apiSuccess {Object} entity.author 讨论话题作者
     * @apiSuccess {String} entity.author.nickname 讨论话题作者昵称
     * @apiSuccess {String} entity.author.avatarUrl 讨论话题作者头像
     * @apiSuccess {Object} entity.readCountDTO 评论总数未读数统计
     * @apiSuccess {Number} entity.readCountDTO.replyTotal 评论总数
     * @apiSuccess {Number} entity.readCountDTO.replyReadTotal  评论已读总数
     * @apiSuccess {Number} entity.readCountDTO.replyNotReadTotal 评论未读总数
     * @apiSuccess {Object[]} entity.sectionList 会话数组 [{班级名称：用户个数}]
     * @apiSuccess {Number} entity.sectionList.sectionId 会话班级ID
     * @apiSuccess {String} entity.sectionList.sectionName 会话班级名称
     * @apiSuccess {Number} entity.sectionList.userCount  会话班级用户个数
     * @apiSuccess {Number} entity.userCount 用户总数，所有班级时才有此字段
     */
    @Override
    public AnnounceVo find(String id) {
        String[] aid_groupId = id.split("_");
        Long aid = Long.valueOf(aid_groupId[0]);
        Long studyGroupId =aid_groupId.length==2?Long.valueOf(aid_groupId[1]):null;
        Announce announce = announceDao.findOne(Announce.builder().id(Long.valueOf(aid)).build());
        if (Objects.isNull(announce)) {
            return null;
        }
        AnnounceVo vo = BeanUtil.beanCopyProperties(announce, AnnounceVo.class);
        //1公告附件节点
        if (Objects.nonNull(vo.getAttachmentFileId())) {
            UserFile userFile = userFileDao.findOne(UserFile.builder().id(vo.getAttachmentFileId()).build());
            vo.setAttachmentFile(userFile);
        }
        // 作者昵称头像，
        UserVo author = userDao.findUserById(vo.getCreateUserId());
        vo.setAuthor(author);
        //公告评论的未读数统计
        List<Long> announceIds = List.of(vo.getId());
        List<ReadCountDTO> unReadList = contentViewRecordDao.findAnnounceOrDiscussionReplyUnreadTotal(studyGroupId, announceIds, WebContext.getUserId(), OriginTypeEnum.ANNOUNCE);
        Map<Long, ReadCountDTO> unReadAnnounceMap = unReadList.stream().collect(Collectors.toMap(ReadCountDTO::getOriginId, a -> a));
        vo.setReadCountDTO(unReadAnnounceMap.get(vo.getId()));

        boolean isGroup = Objects.nonNull(vo.getStudyGroupId());
        if (isGroup) {  //小组公告无分配 无会话 直接返回
            return vo;
        } else {
            //2课程公告分配关联表
            Assign assign = Assign.builder().originType(OriginTypeEnum.ANNOUNCE.getType()).originId(vo.getId()).build();
            List<Assign> assignList = assignDao.find(assign);
            vo.setAssign(assignList);
            //课程公告显示会话 获取此课程会话 班级名称(人数)
            buildSections(Map.of("courseId", vo.getCourseId()+""), List.of(vo));
            return vo;
        }
    }
}

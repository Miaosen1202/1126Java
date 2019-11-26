package com.wdcloud.lms.business.assignment;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.business.assignment.vo.AssignmentReplyVO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignmentReplyTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import com.wdcloud.lms.core.base.model.Grade;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_SUBMIT)
public class AssignmentSubmitDataQuery implements IDataQueryComponent<AssignmentReply> {

    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /assignmentSubmit/get 作业提交详情
     * @apiName assignmentSubmitGet
     * @apiGroup Assignment
     * @apiParam {Number} data 作业ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应描述
     * @apiSuccess {String} entity.submitTime 提交时间
     * @apiSuccess {String} [entity.attachments] 附件列表
     * @apiSuccess {String} [entity.attachments.fileUrl] 附件url
     * @apiSuccess {String} [entity.attachments.fileName] 附件名称
     * @apiSuccess {Number} [entity.score] 老师评的分
     * @apiSuccess {Boolean} entity.isLate 附件名称
     * @apiSuccess {Boolean} entity.username 提交人用户名
     * @apiSuccessExample {String} json 返回值
     * {
     * "code": 200,
     * "entity": {
     * "assignmentId": 1,
     * "attachments": [
     * {
     * "courseId": 1,
     * "fileName": "文件名称",
     * "fileUrl": "xsadasdasdwqafhailusefkashfkl.jpg"
     * }
     * ],
     * "content": "12356154984",
     * "courseId": 1,
     * "id": 3,
     * "isDeleted": 0,
     * "replyType": 1,
     * "submitTime": 1548760218000,
     * "userId": 2,
     * "username": "mizhaoya"
     * },
     * "message": "common.success"
     * }
     */
    @Override
    public AssignmentReply find(String id) {
        Assignment assignment = assignmentDao.get(Long.valueOf(id));
        //最后一次提交
        Example example = assignmentReplyDao.getExample();
        example.orderBy(Constants.PARAM_SUBMIT_TIME).desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.PARAM_ASSIGNMENT_ID, id);
        if (StringUtils.isEmpty(assignment.getStudyGroupSetId())) {
            criteria.andEqualTo(AssignmentReply.USER_ID, WebContext.getUserId());//不是组作业
        } else {
            //获取小组Ids
            List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                    .studyGroupSetId(assignment.getStudyGroupSetId())
                    .userId(WebContext.getUserId())
                    .courseId(assignment.getCourseId()).build());
            List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
            if (ss.size() > 0) {
                criteria.andIn(AssignmentReply.STUDY_GROUP_ID, ss);
            }else{
                criteria.andEqualTo(AssignmentReply.USER_ID, WebContext.getUserId());//没有在小组中
            }
        }
        AssignmentReply reply = assignmentReplyDao.findOne(example);
        if (reply == null) {
            return null;
        }
        AssignmentReplyVO vo = BeanUtil.beanCopyProperties(reply, AssignmentReplyVO.class, Constants.IGNORE_PROPERTIES);
        if (AssignmentReplyTypeEnum.FILE.getType().equals(reply.getReplyType()) ||
                AssignmentReplyTypeEnum.MATE.getType().equals(reply.getReplyType())) {
            //渲染附件
            List<UserFile> files = assignmentReplyDao.assignmentReplyFile(reply.getAssignmentId(), reply.getId());
            vo.setAttachments(files);
        }
        //评分查询
        Example gradeExample = gradeDao.getExample();
        Example.Criteria gradeCriteria = gradeExample.createCriteria();
        gradeCriteria.andEqualTo(Grade.ORIGIN_ID, id);
        gradeCriteria.andEqualTo(Grade.ORIGIN_TYPE, 1);
        gradeCriteria.andEqualTo(Grade.COURSE_ID, assignment.getCourseId());
        gradeCriteria.andEqualTo(Grade.USER_ID, WebContext.getUserId());
        Grade grade = gradeDao.findOne(gradeExample);
        if (grade != null) {
            vo.setScore(grade.getGradeScore());
        }
        //是否late
        example.orderBy(Constants.PARAM_SUBMIT_TIME).asc();
        AssignmentReply firstReply = assignmentReplyDao.findOne(example);
        if (firstReply == null) {
            return null;
        }
        Date firstSubmitTime = firstReply.getSubmitTime();
        AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), OriginTypeEnum.ASSIGNMENT.getType(), Long.valueOf(id));
        Date due = assignUser.getLimitTime();
        Boolean isLate = false;
        if (due!=null) {
            isLate = firstSubmitTime.after(due);
        }
        vo.setIsLate(isLate);
        //提交人
        User user = userDao.get(reply.getUserId());
        vo.setUsername(user.getUsername());
        return vo;
    }
}

package com.wdcloud.lms.business.assignment;

import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_SUBMIT_COMMENT)
public class AssignmentSubmitCommentDataQuery implements IDataQueryComponent<GradeComment> {

    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    /**
     * @api {get} /assignmentSubmitComment/list 作业提交详情评论列表
     * @apiName assignmentSubmitCommentList
     * @apiGroup Assignment
     * @apiParam {Number} assignmentId 作业ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccessExample  {String} json 响应描述
     * {
     *   "code": 200,
     *   "entity": [
     *     {
     *       "content": "这次做完了",
     *       "id": 1,
     *       "username": "Test Teacher"
     *     }
     *   ],
     *   "message": "common.success"
     * }
     */
    @Override
    public List<? extends GradeComment> list(Map<String, String> param) {
        Long id = Long.valueOf(param.get(Constants.PARAM_ASSIGNMENT_ID));
        Assignment assignment = assignmentDao.get(id);
        Map<String, Object> map = Maps.newHashMap();
        map.put(GradeComment.ORIGIN_ID, id);
        if (assignment.getStudyGroupSetId() != null) {
            //查找小组
            StudyGroupUser groupUser = studyGroupUserDao.findJoined(assignment.getStudyGroupSetId(), WebContext.getUserId());
            if (groupUser != null) {
                map.put(GradeComment.STUDY_GROUP_ID, groupUser.getStudyGroupId());
                map.put(GradeComment.USER_ID, WebContext.getUserId());
            }else{
                map.put(GradeComment.USER_ID, WebContext.getUserId());
            }
        } else {
            map.put(GradeComment.USER_ID, WebContext.getUserId());
        }
        return gradeCommentDao.findAssignmentComment(map);
    }
}

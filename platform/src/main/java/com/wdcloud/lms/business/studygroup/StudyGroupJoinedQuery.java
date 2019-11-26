package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP,
        functionName = Constants.FUNCTION_TYPE_JOINED
)
public class StudyGroupJoinedQuery implements ISelfDefinedSearch<List<? extends StudyGroup>> {

    @Autowired
    private StudyGroupDao studyGroupDao;

    /**
     * @api {get} /studyGroup/joined/query 用户加入的小组列表
     * @apiName StudyGroupJoinedQuery
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} [courseId] 课程ID
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 消息
     * @apiSuccess {Object[]} [entity] 小组列表
     * @apiSuccess {Number} entity.id 小组ID
     * @apiSuccess {String} entity.sisId 小组SIS ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {Number} entity.studyGroupSetId 小组集ID
     * @apiSuccess {Number} entity.isStudentGroup 是否学生组
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {Number} entity.maxMemberNumber 成员数量限制
     * @apiSuccess {Number} entity.leaderId 组长ID
     * @apiSuccess {Number} entity.joinType 学生加入小组类型, 1: 无限制 2: 仅限邀请
     *
     */
    @Override
    public List<? extends StudyGroup> search(Map<String, String> condition) {
        List<StudyGroup> joined = studyGroupDao.findJoined(WebContext.getUserId(), null);
        joined.forEach(o -> {
            if(null != o.getMaxMemberNumber() && o.getMaxMemberNumber() == Integer.MAX_VALUE){
                o.setMaxMemberNumber(null);
            }
        });
        return joined;
    }
}

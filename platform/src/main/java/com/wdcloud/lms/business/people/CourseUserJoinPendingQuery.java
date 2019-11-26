package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.vo.CourseUserJoinPendingVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
/**
 * 功能：实现用户班级邀请信息查询接口
 *
 * @author 黄建林
 */
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_JOIN_PENDING)
public class CourseUserJoinPendingQuery implements IDataQueryComponent<CourseUserJoinPendingVO> {

    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;

    /**
     * @api {get} /userJoinPending/list 班级邀请信息查询
     * @apiDescription 班级邀请信息查询
     * @apiName userJoinPendingList
     * @apiGroup People
     * @apiParam {Number} userId 用户ID
     * @apiExample 请求示例:
     * http://localhost:8080/userJoinPending/list?userId=3
     *@apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "code": "CI429894393886259456",
     *             "course": {
     *                 "id": 102,
     *                 "name": "SkrSkrSkr"
     *             },
     *             "courseId": 102,
     *             "createTime": 1553206398000,
     *             "id": 2,
     *             "pRole": {
     *                 "id": 4,
     *                 "roleName": "Student"
     *             },
     *             "publicStatus": 1,
     *             "roleId": 4,
     *             "section": {
     *                 "id": 3,
     *                 "name": "Skr Skr Skr"
     *             },
     *             "sectionId": 3,
     *             "sectionUserId": 41,
     *             "updateTime": 1553206398000,
     *             "userId": 3
     *         },
     *         {
     *             "code": "CI430287201413352512",
     *             "course": {
     *                 "id": 116,
     *                 "name": "赵秀非测试"
     *             },
     *             "courseId": 116,
     *             "createTime": 1553300050000,
     *             "id": 3,
     *             "pRole": {
     *                 "id": 4,
     *                 "roleName": "Student"
     *             },
     *             "publicStatus": 1,
     *             "roleId": 4,
     *             "section": {
     *                 "id": 19,
     *                 "name": "赵秀非测试"
     *             },
     *             "sectionId": 19,
     *             "sectionUserId": 43,
     *             "updateTime": 1553300050000,
     *             "userId": 3
     *         }
     *     ],
     *     "message": "成功"
     * }
     */
    @Override
    public List<? extends CourseUserJoinPendingVO> list(Map<String, String> param) {
        return courseUserJoinPendingDao.getInvitation(Long.valueOf(param.get(Constants.PARAM_USER_ID)), WebContext.getRoleId());
    }

}

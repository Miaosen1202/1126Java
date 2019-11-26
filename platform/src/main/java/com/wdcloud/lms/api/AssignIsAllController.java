package com.wdcloud.lms.api;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.api.dto.AssignIsAllDTO;
import com.wdcloud.lms.api.vo.AssignIsAllVO;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AssignIsAllController {
    @Autowired
    private SectionUserDao sectionUserDao;

    /**
     * @api {post} /assign/isAll 是否分配到所有人
     * @apiName assignIsAll
     * @apiGroup Common
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Object[]} assign 分配
     * @apiParam {Number} [assign.assignId] 分配目标
     * @apiParam {Number=1,2,3} assign.assignType  分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {Object} entity
     * @apiSuccess {Boolean} entity.isAll 是否分配到所有人
     * @apiSuccessExample {String} json
     * {
     *     "code": 200,
     *     "entity": {
     *         "isAll": true
     *     },
     *     "message": "success"
     * }
     */
    @PostMapping("/assign/isAll")
    public String assignIsAll(@RequestBody AssignIsAllDTO assignIsAllDTO, HttpServletRequest request) {
        //数据初始化
        AssignIsAllVO result = new AssignIsAllVO();
        //参数解析
        long courseId = Long.parseLong(assignIsAllDTO.getCourseId());
        List<Assign> assigns = assignIsAllDTO.getAssign();
        //assign分组
        List<Assign> everyone = assigns.stream().filter(o -> (o.getAssignType() != null && o.getAssignType() == 1)).collect(Collectors.toList());
        List<Assign> section = assigns.stream().filter(o -> (o.getAssignType() != null && o.getAssignType() == 2)).collect(Collectors.toList());
        List<Assign> user = assigns.stream().filter(o -> (o.getAssignType() != null && o.getAssignType() == 3)).collect(Collectors.toList());
        //是否有everyone
        if (everyone.size() > 0) {
            result.setIsAll(true);
        }
        //是否有section
        else if (section.size() <= 0) {
             if (user.size() > 0) {
                 //user数==课程总人数
                 List<User> users = sectionUserDao.getUserByCourseId(courseId);//课程总人数
                 if (users.size() == user.size()) {
                     result.setIsAll(true);
                 } else {
                     result.setIsAll(false);
                 }
             }
        }
        //是否有user
        else if (user.size() <= 0) {
            //所选班级人数==课程总人数
            List<Long> sectionIds = section.stream().map(Assign::getAssignId).collect(Collectors.toList());
            List<User> userBySectionIds = sectionUserDao.getUserBySectionId(courseId, sectionIds);//所选班级人数
            List<User> users = sectionUserDao.getUserByCourseId(courseId);//课程总人数
            if (userBySectionIds.size() == users.size()) {
                result.setIsAll(true);
            } else {
                result.setIsAll(false);
            }
        } else {
            //所选班级总人数+user数==课程总人数
            List<Long> sectionIds = section.stream().map(Assign::getAssignId).collect(Collectors.toList());
            List<User> userBySectionIds = sectionUserDao.getUserBySectionId(courseId, sectionIds);//所选班级人数
            List<User> users = sectionUserDao.getUserByCourseId(courseId);//课程总人数
            List<Long> userIds = user.stream().map(Assign::getAssignId).collect(Collectors.toList());
            List<Long> userIdsBySectionIds = userBySectionIds.stream().map(User::getId).collect(Collectors.toList());
            //去重
            Set set = new HashSet();
            set.addAll(userIds);
            set.addAll(userIdsBySectionIds);
            if (set.size()==users.size()) {
                result.setIsAll(true);
            } else {
                result.setIsAll(false);
            }
        }
        return Response.returnResponse(Code.OK, result, "success");
    }
}

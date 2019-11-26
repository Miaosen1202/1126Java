package com.wdcloud.lms.business.assign;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.assign.vo.AssignToVO;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGN)
public class AssignDataQuery implements IDataQueryComponent<Map<String, Object>> {

    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;
    /**
     * @api {get} /assign/get 分配列表下拉数据
     * @apiName assignGet
     * @apiGroup Assign
     * @apiParam {Number} data 课程ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} entity.type 分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiSuccessExample {String} json
     * {
     *   "code": 200,
     *   "entity": {
     *     "sections": [
     *       {
     *         "id": 1,
     *         "name": "Section One",
     *         "type": 2
     *       },
     *       {
     *         "id": 2,
     *         "name": "Section Two",
     *         "type": 2
     *       },
     *       {
     *         "id": 3,
     *         "name": "Section Two",
     *         "type": 2
     *       }
     *     ],
     *     "users": [
     *       {
     *         "id": 2,
     *         "name": "222",
     *         "type": 3
     *       },
     *       {
     *         "id": 3,
     *         "name": "33333",
     *         "type": 3
     *       },
     *       {
     *         "id": 1,
     *         "name": "111",
     *         "type": 3
     *       }
     *     ]
     *   },
     *   "message": "common.success"
     * }
     */
    @Override
    public Map<String, Object> find(String id) {
        Map<String, Object> data = new LinkedHashMap<>();
        //班级
        List<Section> sections = null;
        if (roleService.hasStudentRole()) {//学生
            sections = sectionUserDao.findSectionsByThisStudentJoined(id, WebContext.getUserId(),WebContext.getRoleId());
        }else{//老师
            sections = sectionDao.find(Section.builder().courseId(Long.valueOf(id)).build());
        }
        if (CollectionUtil.isNotNullAndEmpty(sections)) {
            List<AssignToVO> assignTos = new ArrayList<>(sections.size());
            sections.forEach(o -> assignTos.add(AssignToVO.builder()
                    .id(o.getId())
                    .name(o.getName())
                    .type(AssignTypeEnum.SECTION.getType()).build()));
            data.put("sections", assignTos);
        }
        //学生
        List<User> users = sectionUserDao.getUserByCourseId(Long.valueOf(id));
        if (CollectionUtil.isNotNullAndEmpty(sections)) {
            List<AssignToVO> assignTos = new ArrayList<>(users.size());
            users.forEach(o -> assignTos.add(AssignToVO.builder()
                    .id(o.getId())
                    .name(o.getUsername())
                    .type(AssignTypeEnum.USER.getType()).build()));
            data.put("users", assignTos);
        }
        return data;
    }
}

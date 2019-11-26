package com.wdcloud.lms.business.grade;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserParticipateDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SECTION,
        functionName = Constants.FUNCTION_TYPE_PARTICIPATE
)
public class SectionParcitipationQuery implements ISelfDefinedSearch<List<Map<String, Object>>> {

    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    /**
     * @api {get} /section/participate/query Parcitipation图表统计
     * @apiDescription 班级用户在课程内活动列表
     * @apiName sectionParticipateQuery
     * @apiGroup Grade
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} sectionId 班级ID
     * @apiSuccess {Number=200,500} code 响应码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {Object[]} entity.userParticipateList 活动列表
     * @apiSuccess {Number} entity.userParticipateList.id ID
     * @apiSuccess {Number} entity.userParticipateList.courseId 课程ID
     * @apiSuccess {Number} entity.userParticipateList.userId 用户ID
     * @apiSuccess {Number} entity.userParticipateList.originType 活动对象， 1. 作业 2. 讨论 3. 测验 6. 公告  10. 协作文档 11. 网络会议
     * @apiSuccess {Number} entity.userParticipateList.originTd 活动对象ID
     * @apiSuccess {String} entity.userParticipateList.targetName 活动对象名称
     * @apiSuccess {Number} entity.userParticipateList.operation 活动类型 1. 查看（协作文档） 2. 创建（Wiki page） 3. 编辑（协作文档） 4. 提交（作业答案、测验答案、讨论回复、公告回复） 5. 参与（网络会议）
     * @apiSuccess {Date} entity.userParticipateList.createTime 创建时间
     * @apiSuccess {Date} entity.userParticipateList.updateTime 修改时间
     */
    @Override
    public List<Map<String, Object>> search(Map<String, String> condition) {
        Long courseId =Long.parseLong(condition.get("courseId"));
        Long sectionId = Long.parseLong(condition.get("sectionId"));
        SectionUser sectionUser=SectionUser.builder().roleId(RoleEnum.STUDENT.getType()).courseId(courseId).sectionId(sectionId).registryStatus(UserRegistryStatusEnum.JOINED.getStatus()).build();
        //查询在当前课程下，这个班级有多少个学生
        List<SectionUser> sectionUserList = sectionUserDao.find(sectionUser);
        //得到所有学生的userId
        List<Long> userIds=sectionUserList.stream().map(SectionUser::getUserId).collect(Collectors.toList());
        Example example = userParticipateDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(UserParticipate.COURSE_ID, courseId)
                .andIn(UserParticipate.USER_ID, userIds);
        //查询用户活动表
        List<UserParticipate> userParticipateList = userParticipateDao.find(example);
        Map<Long, List<UserParticipate>> userParticipateListMap = userParticipateList.stream().collect(Collectors.groupingBy(UserParticipate::getUserId));
        List<Map<String, Object>> list=new ArrayList<>();
        if (CollectionUtil.isNotNullAndEmpty(userParticipateListMap)) {
            userParticipateListMap.keySet().forEach(userId->{
                list.add(Map.of("userParticipateList", userParticipateListMap.get(userId)));
            });
        }
        return list;
    }
}
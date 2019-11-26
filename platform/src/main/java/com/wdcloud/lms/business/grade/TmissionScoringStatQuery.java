package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.vo.UserSubmitRecordVo;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_GREDE,
        functionName = Constants.FUNCTION_TYPE_TMISSION_SCORING_STAT
)
public class TmissionScoringStatQuery implements ISelfDefinedSearch<Integer> {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    /**
     *
     * @api {get} /grade/tmissionScoringStat/query 评分任务学生提交次数
     * @apiDescription 评分任务是否提交过
     * @apiName tmissionScoringStat
     * @apiGroup Grade
     *
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiParam {Number} originId 任务ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Number} entity 学生提交次数
     */
    @Override
    public Integer search(Map<String, String> condition) {
        if (roleService.hasStudentRole()) {
            throw new PermissionException();
        }else{
            Long originType = Long.valueOf(condition.get("originType"));
            Long originId = Long.valueOf(condition.get("originId"));
            //评分任务 学生是否提交过
            return userSubmitRecordDao.isSubmited(originType,originId);
        }
    }

}

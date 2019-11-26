package com.wdcloud.lms.business.syllabus;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.syllabus.vo.SyllabusListVO;
import com.wdcloud.lms.business.vo.ContentLinkInfoVo;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SYLLABUS,
        functionName = Constants.FUNCTION_TYPE_CALENDAR
)
public class SyllabusCalendarQuery implements ISelfDefinedSearch<List<Integer>> {

    @Autowired
    private SyllabusListQuery syllabusListQuery;

    /**
     * @api {get} /syllabus/calendar/query 大纲日历红点查询
     * @apiName syllabusCalendarQuery
     * @apiGroup Syllabus
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} year 年份
     * @apiParam {Number} month 月份
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Number[]} entity 有任务或事件的日期
     * @apiSuccessExample {String} json 返回值
     * {"code":200,"entity":[22,24],"message":"success"}
     */
    @Override
    public List<Integer> search(Map<String, String> condition) {
        //0、参数解析
        String year = condition.get("year");
        final String month = condition.get("month").length() == 1 ? ("0" + condition.get("month")) : condition.get("month");
        List<Integer> result = Lists.newArrayList();
        //1、获取该课程下所有任务和事件
        Map<String, String> map = new HashMap<>();
        map.put(Constants.PARAM_COURSE_ID, condition.get(Constants.PARAM_COURSE_ID));
        List<SyllabusListVO> list = syllabusListQuery.search(map);
        //2、过滤出指定月的任务和事件
        List<SyllabusListVO> list2 = list.stream().filter(x -> null != x.getLimitTime() && DateUtil.format(DateUtil.hoursOperation(new Date(x.getLimitTime()), 8).getTime(), "yyyy-MM-dd").substring(0, 4).equals(year) && DateUtil.format(DateUtil.hoursOperation(new Date(x.getLimitTime()), 8).getTime(), "yyyy-MM-dd").substring(5, 7).equals(month)).collect(Collectors.toList());
        //3、获取日
        for (SyllabusListVO x: list2) {
            if (null == x.getLimitTime()) {
                continue;
            }
            int day = Integer.parseInt(DateUtil.format(DateUtil.hoursOperation(new Date(x.getLimitTime()), 8).getTime(), "yyyy-MM-dd").substring(8));
            if (!result.contains(day)) {
                result.add(day);
            }
        }
        return result;
    }
}

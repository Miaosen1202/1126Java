package com.wdcloud.lms.base.service;

import com.wdcloud.lms.core.base.vo.Tuple;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.model.TermConfig;
import com.wdcloud.lms.core.base.vo.TermAndConfigVo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TermService {
    @Autowired
    private TermDao termDao;

    /**
     * 解析角色在学期中的开始、结束时间
     * @param termAndConfigVo 学期及角色时间配置
     * @param roleId 角色ID
     * @return (开始时间, 结束时间) 二元组
     */
    public Tuple<Date, Date> getTermRoleStartAndEndTime(@NotNull TermAndConfigVo termAndConfigVo, @NotNull Long roleId) {
        List<TermConfig> configs = termAndConfigVo.getConfigs();
        if (ListUtils.isNotEmpty(configs)) {
            for (TermConfig config : configs) {
                if (Objects.equals(config.getRoleId(), roleId)) {
                    return Tuple.<Date, Date>builder().one(config.getStartTime()).two(config.getEndTime()).build();
                }
            }
        }

        return Tuple.<Date, Date>builder().one(termAndConfigVo.getStartTime()).two(termAndConfigVo.getEndTime()).build();
    }
}

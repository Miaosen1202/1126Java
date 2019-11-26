package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES,
        functionName = Constants.FUNCTION_TYPE_STATUS_LOCKED
)
public class LockedDataSearch implements ISelfDefinedSearch<Object> {
    @Autowired
    private ModuleDao moduleDao;

    /**
     * @api {get} /resource/locked/search 是否锁定
     * @apiName resourceLockedSearch
     * @apiGroup Common
     * @apiParam {Number} originId 资源Id
     * @apiParam {Number} originType 资源类型 1: 作业, 2：讨论 3: 测验，4: 文件 5: 页面
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity success
     */
    @Override
    public Object search(Map<String, String> condition) {
        //todo 第一版本不处理

        //查询 该小项是否被某个单元锁定
        //1 所在模块是否发布
        //2 是否设定解锁时间
        //3 是否有前置模块锁定
        //4 前置模块是否有通过条件
        //5 当前模块是否有通过条件
        List<Module> modules = moduleDao.getModuleByOriginTypeAndOriginId(Long.valueOf(condition.get(Constants.PARAM_ORIGIN_ID)), Integer.valueOf(condition.get(Constants.PARAM_ORIGIN_ID)));
        if (CollectionUtil.isNullOrEmpty(modules)) {
            return null;
        }
        //1 所在模块是否发布 有一个发布了即可
        boolean flag = false;
        List<Integer> statusList = new ArrayList<>(modules.size());
        List<Long> startTimeList = new ArrayList<>(modules.size());

        return null;
    }
}

package com.wdcloud.lms.business.module;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.vo.ContentLinkInfoVo;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE,
        functionName = Constants.FUNCTION_TYPE_LINK_INFO
)
public class ModuleLinkInfoQuery implements ISelfDefinedSearch<List<ContentLinkInfoVo>> {
    @Autowired
    private ModuleDao moduleDao;

    /**
     * @api {get} /module/linkInfo/query 单元链接信息查询
     * @apiName moduleLinkInfo
     * @apiGroup Common
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} entity 链接信息
     * @apiSuccess {Long} entity.courseId 课程
     * @apiSuccess {Long} entity.id 单元ID
     * @apiSuccess {Long} entity.name 名称
     */
    @Override
    public List<ContentLinkInfoVo> search(Map<String, String> condition) {
        if (!condition.containsKey("courseId")) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get("courseId"));
        List<ContentLinkInfoVo> result = new ArrayList<>();
        List<Module> modules = moduleDao.find(Module.builder().courseId(courseId).build());
        for (Module module : modules) {
            ContentLinkInfoVo linkInfoVo = ContentLinkInfoVo.builder()
                    .courseId(courseId)
                    .id(module.getId())
                    .name(module.getName())
                    .build();
            result.add(linkInfoVo);
        }
        return result;
    }
}

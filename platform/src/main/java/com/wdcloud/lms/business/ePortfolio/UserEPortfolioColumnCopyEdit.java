package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioColumnCopyService;
import com.wdcloud.lms.base.service.EPortfolioPageFileService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioItemCopyDTO;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioColumnDTO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE__E_PORTFOLIO_COLUMN,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class UserEPortfolioColumnCopyEdit implements ISelfDefinedEdit {

    @Autowired
    private EPortfolioColumnCopyService copyService;

    /**
     * @api {post} /ePortfolioColumn/copy/edit 复制页面集
     * @apiDescription 复制页面集
     * @apiName ePortfolioColumnCopy
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 页面集id
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 复制后新的页面集ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @ValidationParam(clazz = UserEPortfolioItemCopyDTO.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final UserEPortfolioColumnDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioColumnDTO.class);

        Long id = copyService.copy(dto.getId());

        return new LinkedInfo(String.valueOf(id));
    }
}

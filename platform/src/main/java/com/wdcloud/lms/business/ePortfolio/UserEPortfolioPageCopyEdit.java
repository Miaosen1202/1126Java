package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioPageCopyService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioItemCopyDTO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_E_PORTFOLIO_PAGE,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class UserEPortfolioPageCopyEdit implements ISelfDefinedEdit {

    @Autowired
    private EPortfolioPageCopyService copyService;

    /**
     * @api {post} /ePortfolioPage/copy/edit 复制页面
     * @apiDescription 复制页面
     * @apiName ePortfolioPageCopy
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 页面id
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 复制后新的页面ID
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
        final UserEPortfolioItemCopyDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioItemCopyDTO.class);

        Long newUserEPortfolioPageId = copyService.copy(dto.getId());

        return new LinkedInfo(String.valueOf(newUserEPortfolioPageId));
    }
}

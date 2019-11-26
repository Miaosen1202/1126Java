package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioItemShareDTO;
import com.wdcloud.lms.core.base.dao.UserEPortfolioDao;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_E_PORTFOLIO,
        functionName = Constants.FUNCTION_TYPE_SHARE
)
public class UserEPortfolioShareEdit implements ISelfDefinedEdit {

    @Autowired
    private UserEPortfolioDao userEPortfolioDao;

    /**
     * @api {post} /ePortfolio/share/edit 电子学档分享编辑
     * @apiDescription 分享电子学档
     * @apiName ePortfolioShareEdit
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 电子学档id
     * @apiParam {Number} visibility 可见范围，2: 仅自己可见，3、朋友可见
     * @apiExample {json} 请求示例:
     * {
     *     "id": 11,
     *     "visibility": 3
     * }
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 电子学档id
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "11"
     * }
     *
     */
    @ValidationParam(clazz = UserEPortfolioItemShareDTO.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserEPortfolioItemShareDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioItemShareDTO.class);

        UserEPortfolio ePortfolio = BeanUtil.beanCopyProperties(dto, UserEPortfolio.class);

        userEPortfolioDao.update(ePortfolio);

        return new LinkedInfo(String.valueOf(ePortfolio.getId()));
    }
}

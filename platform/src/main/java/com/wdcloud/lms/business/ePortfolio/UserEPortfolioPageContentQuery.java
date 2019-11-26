package com.wdcloud.lms.business.ePortfolio;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioContentDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioContent;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioContentVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_E_PORTFOLIO_PAGE_CONTENT, description = "电子学档页内容查询")
public class UserEPortfolioPageContentQuery implements IDataQueryComponent {

    @Autowired
    private UserEPortfolioContentDao userEPortfoliosContentDao;

    /**
     * @api {get} /ePortfolioPageContent/get 电子学档页面内容查询
     * @apiDescription 电子学档页面内容
     * @apiName ePortfolioPageContentGet
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} data 页面名称id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} entity 电子学档页面内容
     * @apiSuccess {Number} entity.id 页面内容id
     * @apiSuccess {String} entity.content 页面内容
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "id": 27
     *         "content": "I have moved to this city three months ago. I love the street food here. On weekends,
     *                    I explore new eating joints. This way, I get to learn the routes of this city and prepare
     *                    myself professionally.",
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public UserEPortfolioContentVO find(String id) {
        final Long ePortfolioPageId = Long.valueOf(id);

        UserEPortfolioContent ePortfoliosContent = userEPortfoliosContentDao.getByEPortfoliosPageId(ePortfolioPageId);
        UserEPortfolioContentVO ePortfolioContentVO = BeanUtil.copyProperties(ePortfoliosContent, UserEPortfolioContentVO.class);

        return ePortfolioContentVO;
    }
}

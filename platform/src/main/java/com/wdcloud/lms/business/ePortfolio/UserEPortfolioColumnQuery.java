package com.wdcloud.lms.business.ePortfolio;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioColumnDao;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioColumnVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE__E_PORTFOLIO_COLUMN, description = "电子学档页面集查询")
public class UserEPortfolioColumnQuery implements IDataQueryComponent<UserEPortfolioColumnVO> {

    @Autowired
    private UserEPortfolioColumnDao userEPortfoliosColumnDao;

    /**
     * @api {get} /ePortfolioColumn/get 查询页面集
     * @apiDescription 查询单个页面集
     * @apiName ePortfolioColumnGet
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} data 页面集id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 响应体
     * @apiSuccess {Number} entity.id 页面集id
     * @apiSuccess {String} entity.name 页面集名称
     * @apiSuccess {Object[]} entity.pages 页面集合
     * @apiSuccess {Number} entity.page.id 页面名称id
     * @apiSuccess {String} entity.page.name 页面名称的名字
     * @apiSuccess {Number} entity.page.seq 页面名称排序
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "id": 22,
     *         "name": "Biography-0829-11-1",
     *         "coverColor": "#e6ebf7",
     *         "pages": [
     *             {
     *                 "id": 37,
     *                 "name": "Profile-22-1",
     *                 "seq": 1
     *             },
     *             {
     *                 "id": 38,
     *                 "name": "Profile-22-2",
     *                 "seq": 2
     *             }
     *         ]
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public UserEPortfolioColumnVO find(String id) {
        UserEPortfolioColumnVO userEPortfoliosColumnVO = userEPortfoliosColumnDao.getById(Long.valueOf(id));

        return userEPortfoliosColumnVO;
    }
}

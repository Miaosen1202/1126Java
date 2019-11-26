package com.wdcloud.lms.business.ePortfolio;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.config.LoginInterceptor;
import com.wdcloud.lms.core.base.dao.UserEPortfolioColumnDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioDao;
import com.wdcloud.lms.core.base.enums.UserEPortfolioVisibilityTypeEnum;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioListVO;
import com.wdcloud.lms.core.base.vo.ePortfolio.UserEPortfolioVO;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.LogException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_E_PORTFOLIO, description = "电子学档查询")
public class UserEPortfolioQuery implements IDataQueryComponent<UserEPortfolioVO> {

    @Autowired
    private UserEPortfolioDao userEPortfolioDao;

    /**
     * @api {get} /ePortfolio/list 电子学档列表
     * @apiDescription 查询当前人员所有的电子学档
     * @apiName ePortfolioList
     * @apiGroup ePortfolio
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 电子学档列表
     * @apiSuccess {Number} entity.id 电子学档ID
     * @apiSuccess {String} entity.name 电子学档名称
     * @apiSuccess {Number} entity.columnCount 页面集数量
     * @apiSuccess {Date} entity.updateTime 更新时间
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "id": 11,
     *             "name": "workEPortfolio0828-2",
     *             "columnCount": 4,
     *             "updateTime": 1567092636000
     *         },
     *         {
     *             "id": 12,
     *             "name": "workEPortfolio0828-3",
     *             "columnCount": 2,
     *             "updateTime": 1567092657000
     *         }
     *     ],
     *     "message": "success"
     * }
     */
    @Override
    public List<UserEPortfolioListVO> list(Map param) {
        List<UserEPortfolioListVO> userEPortfolioVOS = userEPortfolioDao.getByUserId(WebContext.getUserId());

        return userEPortfolioVOS;
    }

    /**
     * @api {get} /ePortfolio/get 查询电子学档
     * @apiDescription 查询单个电子学档
     * @apiName ePortfolioGet
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} data 电子学档id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 电子学档列表
     * @apiSuccess {Number} entity.id 电子学档ID
     * @apiSuccess {String} entity.name 电子学档名称
     * @apiSuccess {Object[]} entity.columns 页面集集合
     * @apiSuccess {Number} entity.column.id 页面集集合
     * @apiSuccess {String} entity.column.name 页面集集名称
     * @apiSuccess {String} entity.column.colorCover 页面集颜色
     * @apiSuccess {String} entity.column.x 页面集的横坐标
     * @apiSuccess {String} entity.column.y 页面集的纵坐标
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "id": 11,
     *         "name": "workEPortfolio0828-2"
     *         "columns": [
     *             {
     *                 "id": 22,
     *                 "name": "Biography-0829-11-1",
     *                 "coverColor": "#e6ebf7",
     *                 "seq": 1
     *             },
     *             {
     *                 "id": 23,
     *                 "name": "Biography-0829-11-2",
     *                 "coverColor": "#e6ebf7",
     *                 "seq": 2
     *             }
     *         ],
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public UserEPortfolioVO find(String id) {
        final Long userEPortfolioId = Long.valueOf(id);

        UserEPortfolio userEPortfolio = userEPortfolioDao.get(userEPortfolioId);

        //由于电子学档可以分享，在非登录状态下可查看。只在此处进行了判断
        if(!Objects.equals(WebContext.getUserId(), userEPortfolio.getUserId()) &&
                Objects.equals(userEPortfolio.getVisibility(), UserEPortfolioVisibilityTypeEnum.ONLY_ME.getType()) ){
            throw new BaseException("login.need");
        }else{
            UserEPortfolioVO userEPortfolioVO = userEPortfolioDao.getById(userEPortfolioId);
            return userEPortfolioVO;
        }
    }
}

package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.EPortfolioPageFileService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_E_PORTFOLIO, description = "电子学档编辑")
public class UserEPortfolioEdit implements IDataEditComponent {

    @Autowired
    private UserEPortfolioDao userEPortfoliosDao;
    @Autowired
    private UserEPortfolioPageDao userEPortfoliosPageDao;
    @Autowired
    private UserEPortfolioColumnDao userEPortfoliosColumnDao;
    @Autowired
    private UserEPortfolioContentDao userEPortfoliosContentDao;

    @Autowired
    private EPortfolioPageFileService ePortfolioPageFileService;
    /**
     * @api {post} /ePortfolio/add 新增电子学档
     * @apiDescription 新增电子学档
     * @apiName ePortfolioAdd
     * @apiGroup ePortfolio
     *
     * @apiParam {String} name 电子学档名称
     * @apiExample {json} 请求示例:
     * {
     *     "name": "workEPortfolio"
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 复制后新文件（跟目录）ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @AccessLimit
    @ValidationParam(clazz = UserEPortfolioDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final UserEPortfolioDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioDTO.class);

        UserEPortfolio userEPortfolio = BeanUtil.copyProperties(dto, UserEPortfolio.class);
        userEPortfolio.setUserId(WebContext.getUserId());
        userEPortfoliosDao.save(userEPortfolio);

        return new LinkedInfo(String.valueOf(userEPortfolio.getId()));
    }

    /**
     * @api {post} /ePortfolio/modify 电子学档重命名
     * @apiDescription 电子学档重命名
     * @apiName ePortfolioModify
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 电子学档id
     * @apiParam {String} name 电子学档名称
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1,
     *     "name": "workEPortfolio修改一下"
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 复制后新文件（跟目录）ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @ValidationParam(clazz = UserEPortfolioDTO.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserEPortfolioDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioDTO.class);

        UserEPortfolio userEPortfolio = BeanUtil.copyProperties(dto, UserEPortfolio.class);
        userEPortfoliosDao.update(userEPortfolio);

        return new LinkedInfo(String.valueOf(userEPortfolio.getId()));
    }

    /**
     * @api {post} /ePortfolio/deletes 删除电子学档
     * @apiDescription 编辑电子学档
     * @apiName ePortfolioDelete
     * @apiGroup ePortfolio
     *
     * @apiParam {Number[]} ids 电子学档id
     * @apiExample {json} 请求示例:
     * 1
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 删除的个数
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "1"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long userEPortfolioId = JSON.parseObject(dataEditInfo.beanJson, Long.class);

        UserEPortfolio userEportfolio = userEPortfoliosDao.get(userEPortfolioId);
        if(Objects.isNull(userEportfolio)){
            throw new ParamErrorException();
        }

        ePortfolioPageFileService.deleteByEPortfolioId(userEPortfolioId);

        userEPortfoliosContentDao.deleteByUserEPortfolioId(userEPortfolioId);

        userEPortfoliosPageDao.deleteByUserEPortfolioId(userEPortfolioId);

        userEPortfoliosColumnDao.deleteByEPortfolioId(userEPortfolioId);

        userEPortfoliosDao.delete(userEPortfolioId);

        return new LinkedInfo("1");
    }
}

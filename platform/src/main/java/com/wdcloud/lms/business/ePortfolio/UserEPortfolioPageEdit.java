package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioPageFileService;
import com.wdcloud.lms.base.service.EPortfolioPageSeqMoveService;
import com.wdcloud.lms.base.service.SeqMoveServiceAbstract;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioPageDTO;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.UserEPortfolioContentDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
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

import java.util.List;
import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_E_PORTFOLIO_PAGE, description = "电子学档页面编辑")
public class UserEPortfolioPageEdit implements IDataEditComponent {

    @Autowired
    private UserEPortfolioPageDao userEPortfolioPageDao;
    @Autowired
    private UserEPortfolioContentDao userEPortfolioContentDao;

    @Autowired
    private EPortfolioPageFileService ePortfolioPageFileService;
    @Autowired
    private EPortfolioPageSeqMoveService ePortfolioPageSeqMoveService;

    /**
     * @api {post} /ePortfolioPage/add 新增电子学档页面
     * @apiDescription 新增电子学档页面
     * @apiName ePortfolioPageAdd
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} ePortfolioColumnId 页面集id
     * @apiParam {String} name 页面名称
     * @apiExample {json} 请求示例:
     * {
     *     "ePortfolioColumnId": 1,
     *     "name": "Profile"
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 页面id
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @AccessLimit
    @ValidationParam(clazz = UserEPortfolioPageDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final UserEPortfolioPageDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioPageDTO.class);

        //页面集至少有一个页面。逻辑：新建页面集时，自动创建Home页面。若只有一个页面时，禁止删除。maxSeq最小值为1
        Integer maxSeq = userEPortfolioPageDao.getMaxSeqByEPortfolioColumnId(dto.getEPortfolioColumnId());
        UserEPortfolioPage userEPortfolioPage = BeanUtil.copyProperties(dto, UserEPortfolioPage.class);
        userEPortfolioPage.setSeq(++maxSeq);

        userEPortfolioPageDao.save(userEPortfolioPage);

        return new LinkedInfo(String.valueOf(userEPortfolioPage.getId()));
    }

    /**
     * @api {post} /ePortfolioPage/modify 编辑电子学档页面
     * @apiDescription 编辑电子学档页面
     * @apiName ePortfolioPageModify
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 电子学档页面id
     * @apiParam {String} name 电子学档页面名称
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1,
     *     "name": "MyHome"
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 电子学档页面id
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @ValidationParam(clazz = UserEPortfolioPageDTO.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserEPortfolioPageDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioPageDTO.class);

        UserEPortfolioPage userEPortfolioPage = BeanUtil.copyProperties(dto, UserEPortfolioPage.class);
        userEPortfolioPageDao.update(userEPortfolioPage);

        return new LinkedInfo(String.valueOf(userEPortfolioPage.getId()));
    }

    /**
     * @api {post} /ePortfolioPage/deletes 删除电子学档页面
     * @apiDescription 删除电子学档页面
     * @apiName ePortfolioPageDelete
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 电子学档页面id
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
        final Long userEPortfolioPageId = JSON.parseObject(dataEditInfo.beanJson, Long.class);

        UserEPortfolioPage userEPortfolioPage = userEPortfolioPageDao.get(userEPortfolioPageId);
        if(Objects.isNull(userEPortfolioPage)){
            throw new ParamErrorException();
        }

        List<UserEPortfolioPage> userEPortfolioPages = userEPortfolioPageDao.getByEPortfolioColumnId(
                userEPortfolioPage.getEPortfolioColumnId());

        ePortfolioPageFileService.deleteByEPortfolioPageId(userEPortfolioPageId);

        userEPortfolioContentDao.deleteByUserEPortfolioPageId(userEPortfolioPageId);

        userEPortfolioPageDao.delete(userEPortfolioPageId);

        userEPortfolioPages = ePortfolioPageSeqMoveService.seqChange(SeqConstants.DELETE, userEPortfolioPages,
                SeqConstants.USER_E_PORTFOLIO_PAGE, userEPortfolioPageId, null);
        userEPortfolioPageDao.batchUpdateSeq(userEPortfolioPages);

        return new LinkedInfo("1");
    }
}

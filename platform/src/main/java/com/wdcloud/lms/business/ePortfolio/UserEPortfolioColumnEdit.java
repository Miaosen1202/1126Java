package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioColumnSeqMoveService;
import com.wdcloud.lms.base.service.EPortfolioPageFileService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioColumnDTO;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.UserEPortfolio;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
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
@ResourceInfo(name = Constants.RESOURCE_TYPE__E_PORTFOLIO_COLUMN, description = "电子学档页面集编辑")
public class UserEPortfolioColumnEdit implements IDataEditComponent {

    @Autowired
    private EPortfolioColumnSeqMoveService ePortfolioColumnSeqMoveService;

    @Autowired
    public UserEPortfolioDao userEPortfolioDao;
    @Autowired
    private UserEPortfolioPageDao userEPortfolioPageDao;
    @Autowired
    private UserEPortfolioColumnDao userEPortfolioColumnDao;
    @Autowired
    private UserEPortfolioContentDao userEPortfolioContentDao;

    @Autowired
    private EPortfolioPageFileService ePortfolioPageFileService;

    /**
     * @api {post} /ePortfolioColumn/add 新增页面集
     * @apiDescription 新增页面集
     * @apiName ePortfolioColumnAdd
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} ePortfolioId 电子学档id
     * @apiParam {String} name 页面集名称
     * @apiParam {Number} [coverColor] 页面集名称，未传时默认#e6ebf7
     * @apiExample {json} 请求示例:
     * {
     *     "ePortfolioId": 1,
     *     "name": "Biography",
     *     "coverColor":"#e6ebf7"
     * }
     *
     * @apiSuccess {String} code 业务码
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
    @ValidationParam(clazz = UserEPortfolioColumnDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final UserEPortfolioColumnDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioColumnDTO.class);

        UserEPortfolioColumn userEPortfolioColumn = BeanUtil.copyProperties(dto, UserEPortfolioColumn.class);
        Integer maxSeq = userEPortfolioColumnDao.getMaxSeqByEPortfolioId(userEPortfolioColumn.getEPortfolioId());
        userEPortfolioColumn.setSeq(Objects.isNull(maxSeq) ? 1 : ++maxSeq);
        userEPortfolioColumnDao.save(userEPortfolioColumn);

        //增加一个页面集后，自动创建一个home页面
        UserEPortfolioPage userEPortfolioPage = UserEPortfolioPage.builder()
                .ePortfolioColumnId(userEPortfolioColumn.getId())
                .name(Constants.PARAM_HOME)
                .seq(1)
                .build();
        userEPortfolioPageDao.save(userEPortfolioPage);

        UserEPortfolio userEPortfolio = userEPortfolioDao.get(dto.getEPortfolioId());
        userEPortfolio.setTotalPage(userEPortfolio.getTotalPage() + 1);
        userEPortfolioDao.update(userEPortfolio);

        return new LinkedInfo(String.valueOf(userEPortfolioColumn.getId()));
    }

    /**
     * @api {post} /ePortfolioColumn/modify 编辑页面集信息
     * @apiDescription 编辑页面集，包括名称和背景色
     * @apiName ePortfolioColumnModify
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 页面集id
     * @apiParam {String} [name] 页面集名称，重命名时必传
     * @apiParam {String} [coverColor] 页面集颜色，设置页面预览背景色时必传
     * @apiExample {json} 请求示例:
     * {
     *     "id":1,
     *     "name":"profile"
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
    @ValidationParam(clazz = UserEPortfolioColumnDTO.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserEPortfolioColumnDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioColumnDTO.class);

        if(Objects.isNull(dto.getName()) && Objects.isNull(dto.getCoverColor())){
            throw new ParamErrorException();
        }

        UserEPortfolioColumn userEPortfolioColumn = BeanUtil.copyProperties(dto, UserEPortfolioColumn.class);

        userEPortfolioColumnDao.update(userEPortfolioColumn);

        return new LinkedInfo(String.valueOf(userEPortfolioColumn.getId()));
    }

    /**
     * @api {post} /ePortfolioColumn/deletes 删除页面集
     * @apiDescription 删除页面集
     * @apiName ePortfolioColumnDelete
     * @apiGroup ePortfolio
     *
     * @apiParam {Number[]} id 页面集id
     * @apiExample {json} 请求示例:
     * 1
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 删除个数
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        final Long userEPortfolioColumnId = JSON.parseObject(dataEditInfo.beanJson, Long.class);

        UserEPortfolioColumn userEPortfolioColumn = userEPortfolioColumnDao.get(userEPortfolioColumnId);
        if(Objects.isNull(userEPortfolioColumn)){
            throw new ParamErrorException();
        }

        List<UserEPortfolioColumn> userEPortfolioColumns = userEPortfolioColumnDao
                .getByEPortfolioId(userEPortfolioColumn.getEPortfolioId());

        ePortfolioPageFileService.deleteByEPortfolioColumnId(userEPortfolioColumnId);

        userEPortfolioContentDao.deleteByUserEPortfoliosColumnId(userEPortfolioColumnId);

        userEPortfolioPageDao.deleteByUserEPortfolioColumnId(userEPortfolioColumnId);

        userEPortfolioColumnDao.delete(userEPortfolioColumnId);

        userEPortfolioColumns = ePortfolioColumnSeqMoveService.seqChange(SeqConstants.DELETE, userEPortfolioColumns,
                SeqConstants.USER_E_PORTFOLIO_PAGE, userEPortfolioColumnId, null);
        userEPortfolioColumnDao.batchUpdateSeq(userEPortfolioColumns);

        UserEPortfolio userEPortfolios = userEPortfolioDao.get(userEPortfolioColumn.getEPortfolioId());
        userEPortfolios.setTotalPage(userEPortfolios.getTotalPage() - 1);
        userEPortfolioDao.update(userEPortfolios);

        return new LinkedInfo("1");
    }
}

package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioColumnSeqMoveService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioCommonMoveDTO;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioColumnDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioColumn;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE__E_PORTFOLIO_COLUMN,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class UserEPortfolioColumnMoveEdit implements ISelfDefinedEdit {

    @Autowired
    private UserEPortfolioColumnDao userEPortfolioColumnDao;

    @Autowired
    private EPortfolioColumnSeqMoveService ePortfolioColumnSeqMoveService;

    /**
     * @api {post} /ePortfolioColumn/move/edit 移动页面集
     * @apiDescription 移动页面集
     * @apiName ePortfolioColumnMove
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 页面集id
     * @apiParam {Number} seq 移动的位置
     * @apiExample {json} 请求示例:
     * {
     *     "id": 40,
     *     "seq":2
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 移动页面集的id
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "40"
     * }
     */
    @ValidationParam(clazz = UserEPortfolioCommonMoveDTO.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserEPortfolioCommonMoveDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioCommonMoveDTO.class);

        UserEPortfolioColumn userEPortfolioColumn = userEPortfolioColumnDao.get(dto.getId());
        if(Objects.isNull(userEPortfolioColumn)){
            throw new ParamErrorException();
        }

        List<UserEPortfolioColumn> userEPortfolioColumns = userEPortfolioColumnDao.getByEPortfolioId(userEPortfolioColumn.getEPortfolioId());
        userEPortfolioColumns = ePortfolioColumnSeqMoveService.seqChange(SeqConstants.MOVE, userEPortfolioColumns,
                SeqConstants.USER_E_PORTFOLIO_PAGE, dto.getId(), dto.getSeq());
        userEPortfolioColumnDao.batchUpdateSeq(userEPortfolioColumns);

        return new LinkedInfo(String.valueOf(userEPortfolioColumn.getId()));
    }
}

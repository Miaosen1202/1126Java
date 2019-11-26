package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioPageSeqMoveService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioCommonMoveDTO;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
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
        resourceName = Constants.RESOURCE_TYPE_E_PORTFOLIO_PAGE,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class UserEPortfolioPageMoveEdit implements ISelfDefinedEdit {

    @Autowired
    private UserEPortfolioPageDao userEPortfolioPageDao;

    @Autowired
    private EPortfolioPageSeqMoveService ePortfolioPageSeqMoveService;

    /**
     * @api {post} /ePortfolioPage/move/edit 移动页面
     * @apiDescription 移动页面
     * @apiName ePortfolioPageMove
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} id 文件名id
     * @apiParam {Number} seq 移动的位置
     * @apiExample {json} 请求示例:
     * {
     *     "id": 40,
     *     "seq": 2
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 移动页面的id
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

        UserEPortfolioPage userEPortfolioPage = userEPortfolioPageDao.get(dto.getId());
        if(Objects.isNull(userEPortfolioPage)){
            throw new ParamErrorException();
        }

        List<UserEPortfolioPage> userEPortfolioPages = userEPortfolioPageDao.getByEPortfolioColumnId(
                userEPortfolioPage.getEPortfolioColumnId());
        userEPortfolioPages = ePortfolioPageSeqMoveService.seqChange(SeqConstants.MOVE, userEPortfolioPages,
                SeqConstants.USER_E_PORTFOLIO_PAGE, dto.getId(), dto.getSeq());
        userEPortfolioPageDao.batchUpdateSeq(userEPortfolioPages);

        return new LinkedInfo(String.valueOf(userEPortfolioPage.getId()));
    }
}

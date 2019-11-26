package com.wdcloud.lms.business.ePortfolio;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EPortfolioPageFileService;
import com.wdcloud.lms.business.ePortfolio.dto.UserEPortfolioContentDTO;
import com.wdcloud.lms.core.base.dao.UserEPortfolioContentDao;
import com.wdcloud.lms.core.base.dao.UserEPortfolioPageDao;
import com.wdcloud.lms.core.base.model.UserEPortfolioContent;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_E_PORTFOLIO_PAGE_CONTENT, description = "电子学档页内容编辑")
public class UserEPortfolioPageContentEdit implements IDataEditComponent {

    @Autowired
    private UserEPortfolioContentDao userEPortfolioContentDao;

    @Autowired
    private EPortfolioPageFileService ePortfolioPageFileService;

    /**
     * @api {post} /ePortfolioPageContent/modify 电子学档页面内容保存
     * @apiDescription 电子学档页面内容保存
     * @apiName ePortfolioPageContentModify
     * @apiGroup ePortfolio
     *
     * @apiParam {Number} ePortfolioPageId 页面id
     * @apiParam {String} content 电子学档页面内容
     * @apiParam {String[]} [saveFileUrls] 电子学档页面上传的文件（图片、视频、文件）,没有时传空
     * @apiParam {String[]} [deleteFileUrls] 电子学档页面删除的文件（图片、视频、文件）,没有时传空
     * @apiExample {json} 请求示例:
     *{
     *  "ePortfolioPageId":1,
     *  "content":"I have moved to this city three months ago. I love the street food here.
     * 	          On weekends, I explore new eating joints. This way, I get to learn the routes
     * 	          of this city and prepare myself professionally."
     *  "saveFileUrls":["group1/M00/00/2C/wKgFFFzrkQOAAWWXAAC5TpC8Imw809.png"],
     *  "deleteFileUrls":["group1/M00/00/2C/wKgFFFzrkQOAAWWXAAC5TpC8Imw810.png"]
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 电子学档页面id
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "11"
     * }
     */
    @ValidationParam(clazz = UserEPortfolioContentDTO.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserEPortfolioContentDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserEPortfolioContentDTO.class);

        UserEPortfolioContent userEPortfolioContent = userEPortfolioContentDao.getByEPortfoliosPageId(dto.getEPortfolioPageId());
        if(Objects.isNull(userEPortfolioContent)){
            userEPortfolioContent = BeanUtil.beanCopyProperties(dto, UserEPortfolioContent.class);
            userEPortfolioContentDao.save(userEPortfolioContent);
        }else {
            userEPortfolioContent.setContent(dto.getContent());
            userEPortfolioContentDao.update(userEPortfolioContent);
        }

        ePortfolioPageFileService.add(dto.getEPortfolioPageId(), dto.getSaveFileUrls());
        ePortfolioPageFileService.delete(dto.getEPortfolioPageId(), dto.getDeleteFileUrls());

        return new LinkedInfo(String.valueOf(userEPortfolioContent.getId()));
    }
}


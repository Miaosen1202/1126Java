package com.wdcloud.lms.business.grade;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.dto.GradeCommentDTO;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangxutao
 * 评论信息-删除
 * 在单一评分界面：可以删除评论
 **/
@ResourceInfo(name = Constants.RESOURCE_TYPE_Grade_Comment_Modify)
public class GradeCommentModify implements IDataEditComponent {
    @Autowired
    private GradeCommentDao gradeCommentDao;

    /**
     * @api {post} /gradeCommentEdit/modify 删除评分信息
     * @apiDescription 删除评分信息
     * @apiName gradeCommentEdit
     * @apiGroup GradeGroup
     * @apiParam {Long} id　ID
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 评分
     * @apiSuccess {Long} entity.id 评分内容ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final GradeCommentDTO dto = JSON.parseObject(dataEditInfo.beanJson, GradeCommentDTO.class);
        GradeComment model = gradeCommentDao.get(dto.getId());
        model.setIsDeleted(Status.YES.getStatus());
        gradeCommentDao.update(model);
        return new LinkedInfo(JSON.toJSONString(model.getId()));

    }
}

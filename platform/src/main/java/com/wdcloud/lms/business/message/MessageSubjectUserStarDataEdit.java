package com.wdcloud.lms.business.message;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.message.dto.MessageRecReadDTO;
import com.wdcloud.lms.business.message.dto.MessageStarDTO;
import com.wdcloud.lms.core.base.dao.MessageSubjectUserStarDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.MessageSubjectUserStar;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE_SUBJECT_STAR)
public class MessageSubjectUserStarDataEdit implements IDataEditComponent {

    @Autowired
    private MessageSubjectUserStarDao messageSubjectUserStarDao;


    /**
     * @api {post} /messageStar/add 批量主题(取消)收藏
     * @apiName messageStarAdd
     * @apiGroup message
     * @apiParam {Object[]} subjectIds 主题ID集合
     * @apiParam {Number=0,1} status 收藏状态 1:收藏 0:取消收藏
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity]
     */
    @Override
    @ValidationParam(clazz = MessageStarDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        MessageStarDTO dto = JSON.parseObject(dataEditInfo.beanJson, MessageStarDTO.class);
        if (dto.getStatus().equals(Status.YES.getStatus())) {
            //收藏
            //1获取已经收藏的主题Ids
            Example example = messageSubjectUserStarDao.getExample();
            example.createCriteria().andEqualTo(MessageSubjectUserStar.USER_ID, WebContext.getUserId())
                    .andIn(MessageSubjectUserStar.SUBJECT_ID, dto.getSubjectIds());
            List<MessageSubjectUserStar> staredList=messageSubjectUserStarDao.find(example);
            Set<Long> staredIds=staredList.stream().map(MessageSubjectUserStar::getSubjectId).collect(Collectors.toSet());
            //排除已经收藏过的Ids
            List<Long> finalSubjectIds = dto.getSubjectIds();
            finalSubjectIds.removeAll(staredIds);
            //批量保存 收藏
            if (ListUtils.isNotEmpty(finalSubjectIds)) {
                List<MessageSubjectUserStar> messageSubjectUserStarList = new ArrayList<>();
                finalSubjectIds.forEach(sujectId->{
                    messageSubjectUserStarList.add(MessageSubjectUserStar.builder().userId(WebContext.getUserId()).subjectId(sujectId).build());
                });
                messageSubjectUserStarDao.batchSave(messageSubjectUserStarList);
            }
        }else{
            //取消收藏
            //批量删除
            Example example = messageSubjectUserStarDao.getExample();
            example.createCriteria().andEqualTo(MessageSubjectUserStar.USER_ID, WebContext.getUserId())
                    .andIn(MessageSubjectUserStar.SUBJECT_ID, dto.getSubjectIds());
            messageSubjectUserStarDao.delete(example);
        }
        return new LinkedInfo(String.valueOf(dto.getSubjectIds()), dataEditInfo.beanJson);
    }


    @Override
    @ValidationParam(clazz = MessageRecReadDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        return null;
    }

    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        return null;
    }
}

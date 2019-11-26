package com.wdcloud.lms.business.syllabus;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.syllabus.dto.SyllabusDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.SyllabusDao;
import com.wdcloud.lms.core.base.model.Syllabus;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ResourceInfo(name = Constants.RESOURCE_TYPE_SYLLABUS)
public class SyllabusDataEdit implements IDataEditComponent {

    @Autowired
    private SyllabusDao syllabusDao;

    /**
     * @api {post} /syllabus/add 大纲添加/编辑
     * @apiDescription 大纲添加/编辑
     * @apiName syllabusEdit
     * @apiGroup Syllabus
     * @apiParam {Number} [id] 大纲ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} comments 评论内容
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 大纲ID
     */
    @SuppressWarnings("Duplicates")
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        SyllabusDTO dto = JSON.parseObject(dataEditInfo.beanJson, SyllabusDTO.class);
        Syllabus syllabus = BeanUtil.beanCopyProperties(dto, Syllabus.class);
        Long id = dto.getId();
        if (null == id) {
            //1、排重
            List<Syllabus> list = syllabusDao.find(
                    Syllabus.builder()
                    .courseId(dto.getCourseId())
                    .build());
            if (list.size() > 0) {
                throw new BaseException("syllabus.add.syllabus.exists");
            }
            //2、添加大纲
            syllabusDao.insert(syllabus);
            id = syllabus.getId();
        } else {
            //3、编辑大纲
            syllabusDao.update(syllabus);
        }
        return new LinkedInfo(id.toString());
    }
}

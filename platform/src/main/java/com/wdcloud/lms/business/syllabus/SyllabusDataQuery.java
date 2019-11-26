package com.wdcloud.lms.business.syllabus;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.SyllabusDao;
import com.wdcloud.lms.core.base.model.Syllabus;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@ResourceInfo(name = Constants.RESOURCE_TYPE_SYLLABUS)
public class SyllabusDataQuery implements IDataQueryComponent<Syllabus> {

    @Autowired
    private SyllabusDao syllabusDao;

    /**
     * @api {get} /syllabus/get 大纲详情
     * @apiName syllabusGet
     * @apiGroup Syllabus
     * @apiParam {Number} data 课程ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 大纲详情
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.comments 评论内容
     */
    @Override
    public Syllabus find(String id) {
        Example example = syllabusDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Syllabus.COURSE_ID, id);
        List<Syllabus> syllabuses = syllabusDao.find(example);
        if (syllabuses.size() == 0) {
            return null;
        }
        return syllabuses.get(0);
    }
}

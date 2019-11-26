package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * Favorite课程设置
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_FAVORITE
)
@Slf4j
public class CourseFavoriteEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {post} /course/favorite/edit 用户收藏设置
     * @apiDescription 设置或取消课程Favorite，Favorite课程会出现在Dashboard中
     * @apiName courseFavoriteEdit
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1} favorite 设置Favorite状态，0: 取消， 1: 设置
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 课程ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": "1"
     * }
     */
    @ValidationParam(clazz = FavoriteEditVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        FavoriteEditVo favoriteEditVo = JSON.parseObject(dataEditInfo.beanJson, FavoriteEditVo.class);
        courseUserDao.setCourseFavorite(favoriteEditVo.courseId, WebContext.getUserId(), Status.statusOf(favoriteEditVo.favorite));

        log.info("[CourseFavoriteEdit] set course favorite, courseId={}, favorite={}",
                favoriteEditVo.courseId, Status.statusOf(favoriteEditVo.favorite), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(favoriteEditVo.courseId));
    }

    @Data
    public static class FavoriteEditVo {
        @NotNull
        private Long courseId;
        @NotNull
        private Integer favorite;
    }
}

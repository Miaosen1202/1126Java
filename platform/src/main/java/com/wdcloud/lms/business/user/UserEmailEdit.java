package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.UserEmailDao;
import com.wdcloud.lms.core.base.model.UserEmail;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：实现用户Email表增、删、改调用接口
 *
 * @author 黄建林
 */
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_EMAIL)
public class UserEmailEdit implements IDataEditComponent {
    @Autowired
    private UserEmailDao userEmailDao;
    /**
     * @api {post} /userEmail/add 添加邮箱地址
     * @apiDescription 添加邮箱地址
     * @apiName userEmailAdd
     * @apiGroup User
     *
     * @apiParam {String} email 用户关联邮件地址
     *
     * @apiExample {json} 请求示例:
     * {
     *
     * 	"email": "hjl123456@126.com",
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增测验ID
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "159"
     * }
     */

    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final UserEmail dto = JSON.parseObject(dataEditInfo.beanJson, UserEmail.class);
        int openstatus = userEmailDao.getEmailIsExist(dto.getEmail());
        if (openstatus != 0) {
            throw new BaseException("prop.value.exists2","E-mail",dto.getEmail());
        }
        dto.setUserId(WebContext.getUserId());
        userEmailDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /userEmail/modify 邮箱地址修改
     * @apiDescription 邮箱地址修改
     * @apiName userEmailModify
     * @apiGroup User
     * @apiParam {Number} id ID
     * @apiParam {String} email 用户关联邮件地址
     *
     * @apiExample {json} 请求示例:
     * {
     * 	"id": "159",
     * "email": "hjl123456@126.com",
     *
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增测验ID
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "159"
     * }
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserEmail dto = JSON.parseObject(dataEditInfo.beanJson, UserEmail.class);
        int openstatus = userEmailDao.getEmailIsExist(dto.getEmail());
        if (openstatus != 0) {
            throw new BaseException("prop.value.exists2","E-mail",dto.getEmail());
        }
        userEmailDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /userEmail/deletes 邮箱地址删除
     * @apiDescription 邮箱地址删除
     * @apiName userEmailDeletes
     * @apiGroup User
     * @apiParam {Number[] } ids 邮箱地址ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 邮箱地址ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[49]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除测验里的所有问题及问题附加信息
            userEmailDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }

}

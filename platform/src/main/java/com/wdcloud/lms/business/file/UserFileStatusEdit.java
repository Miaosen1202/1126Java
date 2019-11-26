package com.wdcloud.lms.business.file;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.file.vo.UserFileStatusVo;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileAccessStrategyEnum;
import com.wdcloud.lms.core.base.enums.FilePermissionStatusEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_PUBLISH_STATUS
)
public class UserFileStatusEdit implements ISelfDefinedEdit {
    @Autowired
    private UserFileDao userFileDao;


    /**
     * @api {post} /userFile/status/edit 文件状态修改
     * @apiDescription 文件状态修改，其中，发布状态 1:发布(publish),2:未发布(unpublic),3:限制访问(restricted)；
     * 访问限制, 1: 无限制(public), 2: 限制链接访问(restricted),3: 限制时间访问(restricted), 4: 禁止(unpublic)
     * @apiName UserFileStatusEdit
     * @apiGroup Common
     *
     * @apiParam {Number} id 文件ID
     * @apiParam {Number=1,2,3} status 发布状态 1:发布,2:未发布,3:限制访问
     * @apiParam {Number=2,3} [accessStrategy] 访问限制,2: 限制链接访问,3: 限制时间访问; 只有在限制访问时必传
     * @apiParam {Number} [startTime] 限制访问开始时间; 只有在限制时间访问时必传
     * @apiParam {Number} [endTime] 限制访问结束时间; 只有在限制时间访问时需传，为null,默认没有结束时间设置
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 文件ID
     */
    @ValidationParam(clazz = UserFileStatusVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserFileStatusVo fileStatusVo = JSON.parseObject(dataEditInfo.beanJson, UserFileStatusVo.class);
        UserFile userFile = userFileDao.get(fileStatusVo.getId());
        if (userFile == null) {
            throw new ParamErrorException();
        }

         if (Objects.equals(userFile.getIsSystemLevel(), Status.YES.getStatus())) {
            throw new PermissionException();
         }

        FilePermissionStatusEnum newStatus = FilePermissionStatusEnum.statusOf(fileStatusVo.getStatus());
        if(null == newStatus){
            throw new ParamErrorException();
        }
        userFile.setStatus(newStatus.getStatus());

        FileAccessStrategyEnum accessStrategyEnum = null;
        if(newStatus == FilePermissionStatusEnum.PUBLIC){
            accessStrategyEnum = FileAccessStrategyEnum.NONE;
            userFile.setStartTime(null);
            userFile.setEndTime(null);
        }

        if(newStatus == FilePermissionStatusEnum.UNPUBLIC){
            accessStrategyEnum = FileAccessStrategyEnum.FORBID;
            userFile.setStartTime(null);
            userFile.setEndTime(null);
        }

        if(newStatus == FilePermissionStatusEnum.RESTRICTED){
            accessStrategyEnum =  FileAccessStrategyEnum.strategyOf(fileStatusVo.getAccessStrategy());

            if(accessStrategyEnum == FileAccessStrategyEnum.FORBID || accessStrategyEnum == FileAccessStrategyEnum.NONE){
                throw new ParamErrorException();
            }

            if(accessStrategyEnum == FileAccessStrategyEnum.LINK){
                userFile.setStartTime(null);
                userFile.setEndTime(null);
            }

            if (accessStrategyEnum == FileAccessStrategyEnum.SCHEDULE) {
                if (fileStatusVo.getStartTime() == null) {
                    throw new ParamErrorException();
                }
                userFile.setStartTime(fileStatusVo.getStartTime());
                userFile.setEndTime(fileStatusVo.getEndTime());
            }
        }

        userFile.setAccessStrategy(accessStrategyEnum.getStrategy());

        userFileDao.updateIncludeNull(userFile);

        log.info("[UserFileStatusEdit] user file status edit success, fileId={}, status={}, userId={}",
                userFile.getId(), JSON.toJSONString(fileStatusVo), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(userFile.getId()));
    }
}

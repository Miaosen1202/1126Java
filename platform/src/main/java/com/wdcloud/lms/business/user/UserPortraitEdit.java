package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.user.dto.UserSettingDTO;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 功能：用户头像修改接口
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_SETTING,
        functionName = Constants.FUNCTION_TYPE_PORTRAIT
)
public class UserPortraitEdit implements ISelfDefinedEdit {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserFileService userFileService;

    /**
     * @api {post} /userSetting/portrait/edit 用户头像修改
     * @apiName emailIsExist
     * @apiGroup User
     *
     * @apiParam {Number} id 用户ID
     * @apiParam {String} avatarFileId 用户头像文件url
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     *
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UserSettingDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserSettingDTO.class);
        if (!BeanUtil.checkObject(dto, "id,avatarFileId")) {
            throw new ParamErrorException();
        }

        FileInfo fileInfo = userFileService.getFileInfo(dto.getAvatarFileId());
        if (fileInfo == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_AVATAR_FILE_ID, dto.getAvatarFileId());
        }
        fileInfo.setOriginName(userFileService.getPortraitName(fileInfo));

        UserFile avatarFile = userFileService.saveAvatar(fileInfo);

        userDao.updateAvatar(avatarFile, WebContext.getUserId());

        return new LinkedInfo( String.valueOf(dto.getId()));
    }
}

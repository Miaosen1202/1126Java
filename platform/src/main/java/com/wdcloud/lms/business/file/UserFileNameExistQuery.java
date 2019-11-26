package com.wdcloud.lms.business.file;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.file.vo.UserFileNameExistVo;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.OperateRejectedException;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;

/**
 * 查询文件名是否存在
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_USER_FILE,
        functionName = Constants.FUNCTION_TYPE_NAME
)
public class UserFileNameExistQuery implements ISelfDefinedEdit {

    private static final int OPERATION_ADD = 1;
    private static final int OPERATION_COPY = 2;

    @Autowired
    private UserFileDao userFileDao;

    /**
     * @api {post} /userFile/name/edit 文件(夹)名是否存在
     * @apiDescription 移动文件或文件夹，新增文件或文件夹，判断文件(夹)名是否存在
     * @apiName UserFileNameExistQuery
     * @apiGroup Common
     *
     * @apiParam {Number} [source] 移动文件ID(copy时填写)
     * @apiParam {Number} parentDirectoryId 上级文件夹id(copy时上级文件夹id)
     * @apiParam {Number=0,1} isDirectory 是否是文件夹
     * @apiParam {String} [name] 文件名字(新增时填写)
     * @apiParam {Number=1,2} operateType 操作类型，add为1，copy为2
     *
     * @apiExample {json} 请求示例:
     * {
     *     "parentDirectoryId": 11,
     *     "isDirectory": 1,
     *     "name": "test.png",
     *     "operateType": 1,
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message 返回是否存在
     * {
     *     "code": 200,
     *     "message": "true", //true为存在，false为不存在
     * }
     */
    @ValidationParam(clazz = UserFileNameExistVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo){

        UserFileNameExistVo userFileNameExistVo = JSON.parseObject(dataEditInfo.beanJson, UserFileNameExistVo.class);
        int operateType = userFileNameExistVo.getOperateType();
        Long parentDirectoryId = userFileNameExistVo.getParentDirectoryId();

        boolean nameExist = false;
        switch (operateType) {
            case OPERATION_ADD:
                UserFile parent = userFileDao.findOne(UserFile.builder().id(parentDirectoryId).build());
                if (parent == null) {
                    throw new BaseException("prop.value.not-exists", "parentDirectoryId", String.valueOf(parentDirectoryId));
                }

                if (Objects.equals(parent.getAllowUpload(), Status.NO.getStatus())) {
                    throw new PermissionException();
                }

                String filename = userFileNameExistVo.getName();
                if(StringUtil.isEmpty(filename)){
                    throw new BaseException();
                }
                nameExist = userFileDao.isNameExists(parentDirectoryId, filename, userFileNameExistVo.getIsDirectory());

                break;
            case OPERATION_COPY:
                UserFile source = userFileDao.get(userFileNameExistVo.getSource());
                UserFile target = userFileDao.get(userFileNameExistVo.getParentDirectoryId());
                if (source == null || target == null) {
                    throw new BaseException("file.not-exist.error");
                }

                if(Objects.equals(source.getId(), target.getId())){
                    throw new BaseException("file.path.same.error");
                }

                if (Objects.equals(target.getIsDirectory(), Status.NO.getStatus())) {
                   log.info("[UserFileDataEdit] move file fail, target is not a directory, target={}, opUser={}", target.getId(), WebContext.getUserId());
                    throw new BaseException("file.not-directory.error");
                }

               nameExist = userFileDao.isNameExists(source.getId(), target.getId(), source.getFileName(), source.getIsDirectory());

                break;
            default:
                throw new OperateRejectedException();
        }

        return new LinkedInfo(String.valueOf(nameExist));
    }
}

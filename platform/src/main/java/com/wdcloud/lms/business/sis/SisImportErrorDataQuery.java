package com.wdcloud.lms.business.sis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.SisImportErrorDao;
import com.wdcloud.lms.core.base.model.SisImportError;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_SIS_IMPORT_ERROR)
public class SisImportErrorDataQuery implements IDataQueryComponent<SisImportError> {

    @Autowired
    private SisImportErrorDao sisImportErrorDao;

    /**
     * @api {get} /sisImportError/list 导入错误信息列表
     * @apiName SisImportErrorList
     * @apiGroup SIS
     *
     * @apiParam {String} batchCode 导入批次号
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 相应消息
     * @apiSuccess {Object[]} [entity] 错误列表
     * @apiSuccess {String} entity.batchCode 导入批次号
     * @apiSuccess {String} entity.fileName 文件名
     * @apiSuccess {String} entity.errorCode 错误代码 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复
     * @apiSuccess {Number} [entity.rowNumber] 错误所在行
     * @apiSuccess {String} [entity.fieldName] 字段名称
     * @apiSuccess {String} [entity.fieldValue] 字段值
     *
     */
    @Override
    public List<? extends SisImportError> list(Map<String, String> param) {
        String batchCode = param.get(Constants.PARAM_BATCH_CODE);
        if (StringUtil.isEmpty(batchCode)) {
            throw new ParamErrorException();
        }
        List<SisImportError> sisImportErrors = sisImportErrorDao.find(SisImportError.builder().batchCode(batchCode).build());
        return sisImportErrors;
    }

    /**
     * @api {get} /sisImportError/list 导入错误信息列表分页
     * @apiName SisImportErrorPageList
     * @apiGroup SIS
     *
     * @apiParam {String} batchCode 导入批次号
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 每页结果展示数量
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 相应消息
     * @apiSuccess {Object} [entity] 分页信息
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 每页结果展示数量
     * @apiSuccess {Object[]} entity.list 错误列表
     * @apiSuccess {String} entity.list.batchCode 导入批次号
     * @apiSuccess {String} entity.list.fileName 文件名
     * @apiSuccess {String} entity.list.errorCode 错误代码 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复
     * @apiSuccess {Number} [entity.list.rowNumber] 错误所在行
     * @apiSuccess {String} [entity.list.fieldName] 字段名称
     * @apiSuccess {String} [entity.list.fieldValue] 字段值
     *
     */
    @Override
    public PageQueryResult<? extends SisImportError> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        String batchCode = param.get(Constants.PARAM_BATCH_CODE);
        if (StringUtil.isEmpty(batchCode)) {
            throw new ParamErrorException();
        }
        PageHelper.startPage(pageIndex, pageSize);
        Page<SisImportError> sisImportErrors = (Page<SisImportError>) sisImportErrorDao.find(SisImportError.builder().batchCode(batchCode).build());
        return new PageQueryResult<>(sisImportErrors.getTotal(), sisImportErrors.getResult(), pageSize, pageIndex);
    }
}

package com.wdcloud.lms.business.sis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.sis.vo.SisImportVo;
import com.wdcloud.lms.core.base.dao.SisImportDao;
import com.wdcloud.lms.core.base.dao.SisImportErrorDao;
import com.wdcloud.lms.core.base.dao.SisImportFileDao;
import com.wdcloud.lms.core.base.model.SisImport;
import com.wdcloud.lms.core.base.model.SisImportError;
import com.wdcloud.lms.core.base.model.SisImportFile;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ResourceInfo(
        name = Constants.RESOURCE_TYPE_SIS_IMPORT
)
public class SisImportDataQuery implements IDataQueryComponent<SisImport> {
    @Autowired
    private SisImportDao sisImportDao;
    @Autowired
    private SisImportFileDao sisImportFileDao;
    @Autowired
    private SisImportErrorDao sisImportErrorDao;

    /**
     * @api {get} /sisImport/list 用户导入记录列表
     * @apiDescription 返回数据按导入时间倒序排序
     * @apiName SisImportList
     * @apiGroup SIS
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} [entity] 导入记录列表
     * @apiSuccess {Number} entity.id 记录ID
     * @apiSuccess {String} entity.batchCode 导入批次号
     * @apiSuccess {Number} entity.orgId 导入数据根机构
     * @apiSuccess {Number} entity.startTime 导入开始时间
     * @apiSuccess {Number} [entity.endTime] 导入结束时间，无结束时间时表示导入未完成
     * @apiSuccess {Number=0,1} entity.isFullBatchUpdate 是否全量更新
     * @apiSuccess {Number=0,1} entity.isOverrideUiChange 是否覆盖UI更新
     * @apiSuccess {Number} entity.totalNumber 成功导入记录总数
     * @apiSuccess {Number} entity.orgNumber 成功导入机构数量
     * @apiSuccess {Number} entity.termNumber 成功导入学期数量
     * @apiSuccess {Number} entity.courseNumber 成功导入课程数量
     * @apiSuccess {Number} entity.sectionNumber 成功导入班级数量
     * @apiSuccess {Number} entity.sectionUserNumber 成功导入班级用户数量
     * @apiSuccess {Number} entity.studyGroupSetNumber 成功导入学习小组集数量
     * @apiSuccess {Number} entity.studyGroupNumber 成功导入学习小组数量
     * @apiSuccess {Number} entity.studyGroupUserNumber 成功导入学习小组用户数量
     * @apiSuccess {Number} entity.errorNumber 总错误数量
     * @apiSuccess {Object[]} entity.importFiles 导入文件列表
     * @apiSuccess {String} entity.importFiles.fileName 文件名称
     * @apiSuccess {String} entity.importFiles.fileId 文件ID
     *
     * @apiSuccess {Object[]} [entity.errors] 错误列表
     * @apiSuccess {String} [entity.errors.fileName] 错误所在文件
     * @apiSuccess {Number} [entity.errors.errorCode] 错误码，  1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复
     * @apiSuccess {Number} [entity.errors.rowNumber] 错误所在行
     * @apiSuccess {String} [entity.errors.fieldName] 错误字段名称
     * @apiSuccess {String} [entity.errors.fieldValue] 错误字段值
     *
     */
    @Override
    public List<SisImportVo> list(Map<String, String> param) {
        Example example = sisImportDao.getExample();
        example.createCriteria()
                .andEqualTo(SisImport.OP_USER_ID, WebContext.getUserId());
        example.setOrderByClause(" create_time desc ");

        List<SisImport> sisImports = sisImportDao.find(example);
        return buildResult(sisImports);
    }

    /**
     * @api {get} /sisImport/pageList 用户导入记录列表分页
     * @apiDescription 返回数据按导入时间倒序排序
     * @apiName SisImportPageList
     * @apiGroup SIS
     *
     * @apiParam {Number} entity.pageIndex 页码
     * @apiParam {Number} entity.pageSize 每页结果展示数量
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object} [entity] 导入记录分页信息
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 每页结果展示数量
     * @apiSuccess {Object[]} entity.list 记录列表
     * @apiSuccess {Number} entity.list.id 记录ID
     * @apiSuccess {String} entity.list.batchCode 导入批次号
     * @apiSuccess {Number} entity.list.orgId 导入数据根机构
     * @apiSuccess {Number} entity.list.startTime 导入开始时间
     * @apiSuccess {Number} [entity.list.endTime] 导入结束时间，无结束时间时表示导入未完成
     * @apiSuccess {Number=0,1} entity.list.isFullBatchUpdate 是否全量更新
     * @apiSuccess {Number=0,1} entity.list.isOverrideUiChange 是否覆盖UI更新
     * @apiSuccess {Number} entity.list.totalNumber 成功导入记录总数
     * @apiSuccess {Number} entity.list.orgNumber 成功导入机构数量
     * @apiSuccess {Number} entity.list.termNumber 成功导入学期数量
     * @apiSuccess {Number} entity.list.courseNumber 成功导入课程数量
     * @apiSuccess {Number} entity.list.sectionNumber 成功导入班级数量
     * @apiSuccess {Number} entity.list.sectionUserNumber 成功导入班级用户数量
     * @apiSuccess {Number} entity.list.studyGroupSetNumber 成功导入学习小组集数量
     * @apiSuccess {Number} entity.list.studyGroupNumber 成功导入学习小组数量
     * @apiSuccess {Number} entity.list.studyGroupUserNumber 成功导入学习小组用户数量
     * @apiSuccess {Number} entity.list.errorNumber 总错误数量
     * @apiSuccess {Object[]} entity.list.importFiles 导入文件列表
     * @apiSuccess {String} entity.list.importFiles.fileName 文件名称
     * @apiSuccess {String} entity.list.importFiles.fileId 文件ID
     *
     * @apiSuccess {Object[]} entity.list.errors 错误列表
     * @apiSuccess {String} [entity.list.errors.fileName] 错误所在文件
     * @apiSuccess {Number} [entity.list.errors.errorCode] 错误码，  1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复
     * @apiSuccess {Number} [entity.list.errors.rowNumber] 错误所在行
     * @apiSuccess {String} [entity.list.errors.fieldName] 错误字段名称
     * @apiSuccess {String} [entity.list.errors.fieldValue] 错误字段值
     */
    @Override
    public PageQueryResult<? extends SisImport> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Example example = sisImportDao.getExample();
        example.createCriteria()
                .andEqualTo(SisImport.OP_USER_ID, WebContext.getUserId());
        example.setOrderByClause(" create_time desc ");

        PageHelper.startPage(pageIndex, pageSize);
        Page<SisImport> sisImports = (Page<SisImport>) sisImportDao.find(example);

        List<SisImportVo> result = buildResult(sisImports.getResult());

        return new PageQueryResult<>(sisImports.getTotal(), result, pageSize, pageIndex);
    }

    private List<SisImportVo> buildResult(List<SisImport> sisImports) {
        List<SisImportVo> result = BeanUtil.beanCopyPropertiesForList(sisImports, SisImportVo.class);

        List<String> batchCodes = sisImports.stream().map(SisImport::getBatchCode).collect(Collectors.toList());

        if (ListUtils.isNotEmpty(batchCodes)) {
            Example sisImportFileExample = sisImportFileDao.getExample();
            sisImportFileExample.createCriteria()
                    .andIn(SisImportFile.BATCH_CODE, batchCodes);
            List<SisImportFile> sisImportFiles = sisImportFileDao.find(sisImportFileExample);
            Map<String, List<SisImportFile>> sisImportFileBatchCodeMap = sisImportFiles.stream().collect(Collectors.groupingBy(SisImportFile::getBatchCode));


            Example errorExample = sisImportErrorDao.getExample();
            errorExample.createCriteria()
                    .andIn(SisImportError.BATCH_CODE, batchCodes);
            Map<String, List<SisImportError>> errorBatchCodeMap = sisImportErrorDao.find(errorExample)
                    .stream()
                    .collect(Collectors.groupingBy(SisImportError::getBatchCode));

            for (SisImportVo sisImportVo : result) {
                sisImportVo.setImportFiles(sisImportFileBatchCodeMap.get(sisImportVo.getBatchCode()));
                List<SisImportError> errors = errorBatchCodeMap.get(sisImportVo.getBatchCode());
                sisImportVo.setErrors(errors == null ? new ArrayList<>() : errors);
            }
        }

        return result;
    }
}

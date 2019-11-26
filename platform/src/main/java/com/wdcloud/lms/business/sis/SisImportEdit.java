package com.wdcloud.lms.business.sis;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SIS,
        functionName = Constants.FUNCTION_TYPE_IMPORT
)
public class SisImportEdit implements ISelfDefinedEdit {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private SisImportTask sisImportTask;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private SisImportService sisImportService;

    /**
     * @api /sis/import/edit SIS 数据导入
     * @apiDescription 导入 sis 数据到系统
     * @apiName SisImport
     * @apiGroup SIS
     *
     * @apiParam {Number=0,1} isFullBatchUpdate 是否全量更新
     * @apiParam {Number} [termId] 学期ID
     * @apiParam {String} [zipFileId] zip文件fileId, zipFileId 与 csvFileIds 必填一个，同时存在时 zip 文件优先
     * @apiParam {String[]} [csvFileIds] csv文件列表fileId
     */
    @ValidationParam(clazz = SisImportDto.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        SisImportDto sisImportDto = JSON.parseObject(dataEditInfo.beanJson, SisImportDto.class);

        if (StringUtil.isEmpty(sisImportDto.getZipFileId())
                && ListUtils.isEmpty(sisImportDto.getCsvFileIds())) {
            throw new ParamErrorException();
        }

        List<FileInfo> importFiles = new ArrayList<>();
        if (StringUtil.isNotEmpty(sisImportDto.getZipFileId())) {
            FileInfo zipFileInfo = userFileService.getFileInfo(sisImportDto.getZipFileId());
            if (zipFileInfo == null) {
                throw new PropValueUnRegistryException(Constants.PARAM_ZIP_FILE_ID, sisImportDto.getZipFileId());
            }

            importFiles.add(zipFileInfo);
        } else {
            for (String csvFileId : sisImportDto.getCsvFileIds()) {
                FileInfo fileInfo = userFileService.getFileInfo(csvFileId);
                if (fileInfo == null) {
                    throw new PropValueUnRegistryException(Constants.PARAM_CSV_FILE_IDS, csvFileId);
                }

                importFiles.add(fileInfo);
            }
        }

        if (ListUtils.isEmpty(importFiles)) {
            throw new BaseException("sis.valid.file.empty");
        }

        if (Objects.equals(sisImportDto.getIsFullBatchUpdate(), Status.YES.getStatus())) {
            if (sisImportDto.getTermId() == null) {
                throw new ParamErrorException();
            }
            Term term = termDao.get(sisImportDto.getTermId());
            if (term == null || Objects.equals(term.getIsDefault(), Status.NO.getStatus()) && StringUtil.isEmpty(term.getSisId())) {
                throw new ParamErrorException();
            }
        }

        Org rootOrg = orgDao.get(WebContext.getOrgId());
        if (!Objects.equals(rootOrg.getType(), OrgTypeEnum.SCHOOL.getType())) {
            throw new PermissionException();
        }

        if (StringUtil.isEmpty(rootOrg.getSisId())) {
            throw new BaseException("sis.import.org.sis-id.required");
        }

        if (!sisImportService.lock(rootOrg)) {
            throw new BaseException("sis.import.org.process");
        }

        String batchCode = sisImportService.generateBatchCode();
        Long opUserId = WebContext.getUserId();
        threadPoolTaskExecutor.execute(() -> sisImportTask.handle(sisImportDto, batchCode, importFiles, opUserId, rootOrg));

        return new LinkedInfo(batchCode);
    }

    @Data
    public static class SisImportDto {
        private Integer isFullBatchUpdate;
        private Long termId;
        private List<String> csvFileIds;
        private String zipFileId;
    }
}

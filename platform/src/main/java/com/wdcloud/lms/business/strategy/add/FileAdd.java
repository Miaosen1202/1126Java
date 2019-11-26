package com.wdcloud.lms.business.strategy.add;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.file.vo.UserFileEditVo;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractFileStrategy;
import com.wdcloud.lms.core.base.enums.FileUsageEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class FileAdd extends AbstractFileStrategy implements AddStrategy{
    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        //0、参数解析
        if (null == moduleItemContentDTO.getParentDirectoryId() || null == moduleItemContentDTO.getFileId() || "".equals(moduleItemContentDTO.getFileId())) {
            throw new ParamErrorException();
        }
        //1、拼凑dataEditInfo
        UserFileEditVo userFileEditVo = UserFileEditVo.builder()
                .isDirectory(Status.NO.getStatus())
                .name(moduleItemContentDTO.getTitle())
                .fileId(moduleItemContentDTO.getFileId())
                .parentDirectoryId(moduleItemContentDTO.getParentDirectoryId())
                .build();
        String beanJson = JSON.toJSONString(userFileEditVo);
        DataEditInfo dataEditInfo = new DataEditInfo(beanJson);
        //2、添加文件
        LinkedInfo linkedInfo = userFileDataEdit.add(dataEditInfo);
        //3、返回文件ID
        return Long.valueOf(linkedInfo.masterId);
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }
}

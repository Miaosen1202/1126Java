package com.wdcloud.lms.business.sis;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.sis.vo.SisImportProcessRecode;
import com.wdcloud.lms.core.base.dao.SisImportDao;
import com.wdcloud.lms.core.base.model.SisImport;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

import static com.wdcloud.lms.business.sis.SisImportProcessQuery.SisImportProcessVo;
import static com.wdcloud.lms.business.sis.vo.SisImportProcessRecode.ProcessPhase;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SIS,
        functionName = Constants.FUNCTION_TYPE_IMPORT_PROCESS
)
public class SisImportProcessQuery implements ISelfDefinedSearch<SisImportProcessVo> {
    @Autowired
    private SisImportDao sisImportDao;
    @Autowired
    private SisImportService sisImportService;

    /**
     * @api {get} /sis/importProcess/query 用户导入任务进度查询
     * @apiName SisImportProcessQuery
     * @apiGroup SIS
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} message 相应消息
     * @apiSuccess {Object} [entity] 处理进度
     * @apiSuccess {String} [entity.batchCode] 最近导入任务批次号，如果批次号未返回，表示用户没有执行过导入任务
     * @apiSuccess {Number} entity.percent 处理进度（整数表示百分比）
     *
     */
    @Override
    public SisImportProcessVo search(Map<String, String> condition) {

        SisImport lastImport = sisImportDao.findLastImport(WebContext.getUserId());
        if (lastImport == null) {
            return new SisImportProcessVo("");
        }

        String batchCode = lastImport.getBatchCode();
        SisImportProcessVo result = new SisImportProcessVo(batchCode);

        if (lastImport.getEndTime() != null) {
            result.setPercent(100);
        } else {
            SisImportProcessRecode processRecode = sisImportService.getProcessRecord(batchCode);
            if (processRecode != null) {
                result.setPercent(processRecode.getTotalPercent());
            }
        }

        return result;
    }

    @Data
    public static class SisImportProcessVo {
        private String batchCode;
        private int percent;

        public SisImportProcessVo(String batchCode) {
            this.batchCode = batchCode;
        }
    }
}

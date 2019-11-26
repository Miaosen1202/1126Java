package com.wdcloud.lms.business.sis;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.sis.vo.SisImportProcessRecode;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.idgenerate.IdGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SisImportService {
    private static final long PROCESS_RECORD_FINISH_CACHE_SECOND = 120;

    private static final int PROCESS_RECODE_TOTAL_PERCENT_MAX = 100;

    @Autowired
    private IRedisService redisService;

    public boolean lock(Org rootOrg) {
        return redisService.lock(Constants.SIS_IMPORT_PROCESS_ORG_PRE + rootOrg.getId(), 10);
    }

    public void unlock(Org rootOrg) {
        redisService.unLock(Constants.SIS_IMPORT_PROCESS_ORG_PRE + rootOrg.getId());
    }

    public SisImportProcessRecode getProcessRecord(String batchCode) {
        String processRecord = redisService.get(getProcessCacheKey(batchCode));
        if (StringUtil.isNotEmpty(processRecord)) {
            SisImportProcessRecode sisImportProcessRecode = JSON.parseObject(processRecord, SisImportProcessRecode.class);
            return sisImportProcessRecode;
        }
        return null;
    }

    public void updateProcess(String batchCode, SisImportProcessRecode.ProcessPhase phase, int percent, int totalPercent) {
        SisImportProcessRecode processRecord = SisImportProcessRecode.builder()
                .phase(phase)
                .percent(percent)
                .batchCode(batchCode)
                .totalPercent(totalPercent)
                .build();
        String processCacheKey = getProcessCacheKey(batchCode);
        redisService.set(processCacheKey, JSON.toJSONString(processRecord));

        if (totalPercent >= PROCESS_RECODE_TOTAL_PERCENT_MAX) {
            redisService.expire(processCacheKey, PROCESS_RECORD_FINISH_CACHE_SECOND);
        }
    }

    public void updateProcess(String batchCode, SisImportProcessRecode.ProcessPhase phase, int percent) {
        updateProcess(batchCode, phase, percent, calcTotalPercent(phase, percent));
    }

    public void updateTotalPercent(String batchCode, int percent) {
        SisImportProcessRecode processRecord = getProcessRecord(batchCode);
        processRecord.setTotalPercent(percent);
        redisService.set(getProcessCacheKey(batchCode), JSON.toJSONString(processRecord));
    }

    /**
     * 计算处理总进度
     * 导入处理分3个阶段及其： 文件检查（10）、导入中间表（45）、传输到业务表（45）
     * @param phase 处理阶段
     * @param percent 阶段处理进度
     * @return
     */
    public int calcTotalPercent(SisImportProcessRecode.ProcessPhase phase, int percent) {
        int totalPercent = PROCESS_RECODE_TOTAL_PERCENT_MAX;
        switch (phase) {
            case FILE_CHECK:
                totalPercent = ((int) (percent * 0.1));
                break;
            case IMPORT:
                totalPercent =  ((int) (percent * 0.45) + 10);
                break;
            case TRANSFER:
                totalPercent = ((int) (percent * 0.45) + 55);
                break;
            default:
        }
        return totalPercent;
    }

    public String getProcessCacheKey(String batchCode) {
        return Constants.SIS_IMPORT_PROCESS_USER_PRE + batchCode;
    }

    /**
     * 返回批次号，该值不会重复
     * @return
     */
    public String generateBatchCode() {
        return IdGenerateUtils.getId("", Short.MAX_VALUE * 2);
    }
}

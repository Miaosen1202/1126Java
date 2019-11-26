package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportTermDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportTerm;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TermReader extends AbstractSisCsvReader<SisImportTerm> {
    private static final String FIELD_TERM_ID = "term_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_START_DATE = "start_date";
    private static final String FIELD_END_DATE = "end_date";
    private static final String FIELD_STATUS = "status";

    private SisImportTermDao sisImportTermDao;
    private Org rootOrg;

    public TermReader(Path csvFile, Org rootOrg, SisImportTermDao sisImportTermDao) {
        super(
                csvFile,
                Set.of(FIELD_TERM_ID, FIELD_NAME, FIELD_START_DATE, FIELD_END_DATE, FIELD_STATUS),
                Set.of(FIELD_TERM_ID, FIELD_NAME, FIELD_STATUS)
        );
        this.rootOrg = rootOrg;
        this.sisImportTermDao = sisImportTermDao;
    }

    @Override
    protected List<SisImportTerm> checkRecord(List<RecordVo<SisImportTerm>> recordVos) {
        List<String> termIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(a -> a.getTermId())
                .collect(Collectors.toList());

        Map<String, SisImportTerm> sysTermIdMap = sisImportTermDao.findByTermIds(termIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportTerm::getTermId, a -> a));

        List<SisImportTerm> validImportVos = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportTerm> recordVo : recordVos) {
            SisImportTerm record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportTerm sisImportTerm = sysTermIdMap.get(record.getTermId());
            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType())
                    && sisImportTerm == null) {

                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_TERM_ID, record.getTermId()));
                continue;
            }
            if (sisImportTerm != null) {
                record.setId(sisImportTerm.getId());
            }

            validImportVos.add(record);
        }

        return validImportVos;
    }

    @Override
    protected SisImportTerm buildRecord(Map<String, String> record, long row) throws ReaderException {
        SisImportTerm importTerm = SisImportTerm.builder()
                .termId(record.get(FIELD_TERM_ID))
                .name(record.get(FIELD_NAME))
                .startDate(record.get(FIELD_START_DATE))
                .endDate(record.get(FIELD_END_DATE))
                .operation(record.get(FIELD_STATUS))
                .build();
        try {
            parseDate(importTerm.getStartDate());
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_START_DATE, importTerm.getStartDate()));
        }
        try {
            parseDate(importTerm.getEndDate());
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_END_DATE, importTerm.getEndDate()));
        }
        return importTerm;
    }

    @Override
    protected String getUniqueKey(SisImportTerm record) {
        return FIELD_TERM_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportTerm record) {
        return record.getTermId();
    }
}

package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportTerm;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TermTransfer {

    @Autowired
    private TermDao termDao;


    public List<Term> transfer(List<SisImportTerm> importTerms, Long opUserId, Org rootOrg) {
        List<SisImportTerm> deleteTerm = importTerms.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isNotEmpty(deleteTerm)) {
            termDao.deleteBySisIds(rootOrg.getTreeId(), deleteTerm.stream().map(SisImportTerm::getTermId).collect(Collectors.toSet()));
        }


        List<SisImportTerm> activeTerms = importTerms.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());
        if (ListUtils.isEmpty(activeTerms)) {
            return new ArrayList<>();
        }

        List<Term> existsTerms =
                termDao.findBySisIds(rootOrg.getTreeId(), activeTerms.stream().map(SisImportTerm::getTermId).collect(Collectors.toSet()));
        Map<String, Term> existsTermSisIdMap = existsTerms.stream()
                .collect(Collectors.toMap(Term::getSisId, a -> a));

        List<Term> result = new ArrayList<>();
        for (SisImportTerm term : activeTerms) {

            Date startDate = parseDate(term.getStartDate());
            Date endDate = parseDate(term.getEndDate());

            if (existsTermSisIdMap.containsKey(term.getTermId())) {
                Term exists = existsTermSisIdMap.get(term.getTermId());
                exists.setName(term.getName());
                exists.setStartTime(startDate);
                exists.setEndTime(endDate);
                exists.setCreateUserId(opUserId);
                exists.setUpdateUserId(opUserId);
                termDao.updateIncludeNull(exists);
            } else {
                Term newTerm = Term.builder()
                        .isDefault(Status.NO.getStatus())
                        .name(term.getName())
                        .code(term.getName())
                        .sisId(term.getTermId())
                        .startTime(startDate)
                        .endTime(endDate)
                        .orgId(rootOrg.getId())
                        .orgTreeId(rootOrg.getTreeId())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                termDao.save(newTerm);

                result.add(newTerm);
            }
        }

        return result;
    }

    protected static final String[] DATE_TIME_FORMATS = new String[]{"YYYY-MM-DD HH:mm:ss", "YYYY-MM-DD'T'HH:mm:ss",
            "YYYY-MM-DD HH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSSZZ"};

    private Date parseDate(String date) {
        if (StringUtil.isNotEmpty(date)) {
            try {
                return DateUtils.parseDate(date, DATE_TIME_FORMATS);
            } catch (ParseException e) {
            }
        }
        return null;
    }
}

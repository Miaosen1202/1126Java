package com.wdcloud.lms.business.sis.reader;

import com.google.common.base.Joiner;
import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.sis.enums.SisRoleEnum;
import com.wdcloud.lms.core.base.dao.SisImportCourseDao;
import com.wdcloud.lms.core.base.dao.SisImportSectionDao;
import com.wdcloud.lms.core.base.dao.SisImportSectionUserDao;
import com.wdcloud.lms.core.base.dao.SisImportUserDao;
import com.wdcloud.lms.core.base.model.*;
import tk.mybatis.mapper.entity.Example;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SectionUserReader extends AbstractSisCsvReader<SisImportSectionUser> {
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_USER_ID = "user_id";
    private static final String FIELD_ROLE = "role";
    private static final String FIELD_SECTION_ID = "section_id";
    private static final String FIELD_STATUS = "status";

    private static final Set<String> HEADERS = Set.of(FIELD_COURSE_ID, FIELD_USER_ID, FIELD_ROLE, FIELD_SECTION_ID, FIELD_STATUS);
    private static final Set<String> REQUIRED_FIELD = Set.of(FIELD_COURSE_ID, FIELD_USER_ID, FIELD_ROLE, FIELD_SECTION_ID, FIELD_STATUS);

    private Org opUserOrg;
    private SisImportCourseDao sisImportCourseDao;
    private SisImportSectionDao sisImportSectionDao;
    private SisImportUserDao sisImportUserDao;
    private SisImportSectionUserDao sisImportSectionUserDao;

    public SectionUserReader(Path csvFile, Org opUserOrg, SisImportCourseDao sisImportCourseDao,
                             SisImportSectionDao sisImportSectionDao, SisImportUserDao sisImportUserDao,
                             SisImportSectionUserDao sisImportSectionUserDao) {
        super(
                csvFile,
                HEADERS,
                REQUIRED_FIELD
        );

        this.opUserOrg = opUserOrg;
        this.sisImportCourseDao = sisImportCourseDao;
        this.sisImportSectionDao = sisImportSectionDao;
        this.sisImportUserDao = sisImportUserDao;
        this.sisImportSectionUserDao = sisImportSectionUserDao;
    }

    @Override
    protected List<SisImportSectionUser> checkRecord(List<RecordVo<SisImportSectionUser>> recordVos) {
        Set<String> courseIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportSectionUser::getCourseId)
                .collect(Collectors.toSet());
        Set<String> sectionIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportSectionUser::getSectionId)
                .collect(Collectors.toSet());
        Set<String> userIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportSectionUser::getUserId)
                .collect(Collectors.toSet());

        Map<String, SisImportCourse> sysCourseIdMap = sisImportCourseDao.findByCourseIds(courseIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportCourse::getCourseId, a -> a));
        Map<String, SisImportSection> sysSectionIdMap = sisImportSectionDao.findBySectionIds(sectionIds, opUserOrg.getTreeId())
                .stream()
                .filter(a -> sysCourseIdMap.containsKey(a.getCourseId()))
                .collect(Collectors.toMap(SisImportSection::getSectionId, a -> a));

        Map<String, SisImportUser> sysUserIdMap = sisImportUserDao.findByUserIds(userIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportUser::getUserId, a -> a));

        Example example = sisImportSectionUserDao.getExample();
        example.createCriteria()
                .andIn(SisImportSectionUser.SECTION_ID, sectionIds)
                .andIn(SisImportSectionUser.USER_ID, userIds)
                .andLike(SisImportSectionUser.ORG_TREE_ID, opUserOrg.getTreeId() + "%");
        Map<String, SisImportSectionUser> importSectionUserMap = sisImportSectionUserDao.find(example)
                .stream()
                .collect(Collectors.toMap(a -> Joiner.on("-").join(a.getCourseId(), a.getSectionId(), a.getUserId(), a.getRole()), a -> a));

        List<SisImportSectionUser> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportSectionUser> recordVo : recordVos) {
            SisImportSectionUser record = recordVo.getRecord();
            long row = recordVo.getRow();

            String key = Joiner.on("-").join(record.getCourseId(), record.getSectionId(), record.getUserId(), record.getRole());
            SisImportSectionUser sysSectionUser = importSectionUserMap.get(key);

            SisImportCourse sysCourse = sysCourseIdMap.get(record.getCourseId());
            if (sysCourse == null || Objects.equals(sysCourse.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_COURSE_ID, record.getCourseId()));
                continue;
            }

            SisImportSection sysSection = sysSectionIdMap.get(record.getSectionId());
            if (sysSection == null || Objects.equals(sysSection.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_SECTION_ID, record.getSectionId()));
                continue;
            }

            if (!Objects.equals(sysSection.getCourseId(), sysCourse.getCourseId())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_SECTION_ID, record.getSectionId()));
                continue;
            }

            SisImportUser sysUser = sysUserIdMap.get(record.getUserId());
            if (sysUser == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_USER_ID, record.getUserId()));
                continue;
            }

            if (sysSectionUser == null && Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_USER_ID, record.getUserId()));
                continue;
            }

            if (sysSectionUser != null) {
                record.setId(sysSectionUser.getId());
            }

            validImports.add(record);
        }
        return validImports;
    }

    @Override
    protected SisImportSectionUser buildRecord(Map<String, String> record, long row) throws ReaderException {
        SisImportSectionUser sectionUser = SisImportSectionUser.builder()
                .courseId(record.get(FIELD_COURSE_ID))
                .sectionId(record.get(FIELD_SECTION_ID))
                .userId(record.get(FIELD_USER_ID))
                .role(record.get(FIELD_ROLE))
                .operation(record.get(FIELD_STATUS))
                .build();

        String role = sectionUser.getRole();
        if (SisRoleEnum.nameOf(role) == null) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_ROLE, role));
        }

        return sectionUser;
    }

    @Override
    protected String getUniqueKey(SisImportSectionUser record) {
        return FIELD_COURSE_ID + "-" + FIELD_SECTION_ID + "-" + FIELD_USER_ID + "-" + FIELD_ROLE;
    }

    @Override
    protected String getUniqueKeyVal(SisImportSectionUser record) {
        return record.getCourseId() + "-" + record.getSectionId() + "-" + record.getUserId() + "-" + record.getRole();
    }

    @Override
    protected Set<OperationTypeEnum> getSupportOps() {
        return Set.of(OperationTypeEnum.ACTIVE, OperationTypeEnum.INACTIVE, OperationTypeEnum.DELETED);
    }
}

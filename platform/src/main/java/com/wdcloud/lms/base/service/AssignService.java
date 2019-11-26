package com.wdcloud.lms.base.service;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.vo.Tuple;
import com.wdcloud.server.frame.exception.ParamErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AssignService {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private AssignUserService assignUserService;

    public List<Assign> findAssigns(Long courseId, List<Tuple<OriginTypeEnum, Long>> originTypeAndIds, Collection<Long> sectionIds) {
        List<Assign> assigns = assignDao.findAssigns(courseId, originTypeAndIds);
        return getAssociateAssign(sectionIds, assigns);
    }

    /**
     * 跟当前用户关联且有效的分配
     *
     * @param sectionIds
     * @param assigns
     * @return
     */

    public List<Assign> getAssociateAssign(Collection<Long> sectionIds, Collection<Assign> assigns) {
        List<Assign> result = new ArrayList<>();
        Assign all = null;
        boolean hasAssign = false;
        for (Assign assign : assigns) {
            if (AssignTypeEnum.ALL.getType().equals(assign.getAssignType())) {
                all = assign;
            } else if (AssignTypeEnum.SECTION.getType().equals(assign.getAssignType())) {
                if (sectionIds.contains(assign.getAssignId())) {
                    hasAssign = true;
                    result.add(assign);
                }
            } else if (AssignTypeEnum.USER.getType().equals(assign.getAssignType())) {
                if (Objects.equals(assign.getAssignId(), WebContext.getUserId())) {
                    hasAssign = true;
                    result.add(assign);
                }
            }
        }
        if (!hasAssign && all != null) {
            result.add(all);
        }

        return result;
    }

    public void batchSave(List<Assign> assigns, long courseId, OriginTypeEnum originType, long originId) {
        validAssign(assigns, courseId);

        assignDao.deleteByField(Assign.builder().courseId(courseId).originId(originId).originType(originType.getType()).build());
        for (Assign assign : assigns) {
            assign.setOriginId(originId);
            assign.setOriginType(originType.getType());
            assign.setCourseId(courseId);
            assign.setCreateUserId(WebContext.getUserId());
            assign.setUpdateUserId(WebContext.getUserId());
        }
        assignDao.batchInsert(assigns);
        //把数据存入户分配表中
        assignUserService.batchSave(assigns, false);

        log.info("[AssignService] batch save assign success, courseId={}, originId={}, originType={}, assignNumber={}",
                courseId, originId, originType, assigns.size());
    }

    /**
     * 分配校验
     * 规则：同一对象只能分配一次。
     * 如果设置了时间，开始时间必须小于结束时间，截止时间必须在开始时间与结束时间之间。
     * // 如果分配对象为班级，且班级设置了单独时间，开始、结束时间必须在班级时间范围内；
     * 否则如果课程设置了时间，开始、结束时间必须在课程时间范围内；
     * 否则如果学期设置了时间，开始、结束时间必须在学期时间范围内。
     *
     * @param assigns 分配列表，不能为空
     * @throws BaseException       当校验时间范围错误时
     * @throws ParamErrorException assigns为空， 或courseId对应课程不存在，或分配对象指定为班级，但是班级在课程下不存在,
     */
    public void validAssign(List<Assign> assigns, long courseId) {
        if (assigns == null) {
            throw new ParamErrorException();
        }

        validAssignDuplicate(assigns);

        Course course = courseDao.get(courseId);
//        CourseConfig courseConfig = courseConfigDao.getByCourseId(courseId);
        if (course == null) {
            throw new ParamErrorException();
        }

        Term term = termDao.get(course.getTermId());

//        List<Long> sectionIds = assigns.stream()
//                .filter(assign -> AssignTypeEnum.typeOf(assign.getAssignType()) == AssignTypeEnum.SECTION)
//                .map(Assign::getAssignId)
//                .collect(Collectors.toList());
//        List<Section> sections = sectionDao.findCourseSections(sectionIds, courseId);
//        Map<Long, Section> sectionMap = sections.stream().collect(Collectors.toMap(Section::getId, a -> a));
//        if (sectionIds.size() != sections.size()) {
//            for (Long sectionId : sectionIds) {
//                if (!sectionMap.containsKey(sectionId)) {
//                    log.info("[AssignService] assign section not exists, courseId={}, sectionId={}, exists sectionIds={}",
//                            courseId, sectionId, JSON.toJSONString(sectionMap.keySet()));
//                    throw new BaseException("assign.section.invalid", String.valueOf(sectionId));
//                }
//            }
//        }

        for (Assign assign : assigns) {
            Date startTime = assign.getStartTime();
            Date endTime = assign.getEndTime();
            Date limitTime = assign.getLimitTime();

            if (startTime != null && endTime != null && startTime.after(endTime)) {
                throw new BaseException("assign.start-time.after.end-time");
            }

            if (limitTime != null) {
                if (startTime != null && limitTime.before(startTime)) {
                    throw new BaseException("assign.due-time.before.start-time");
                }
                if (endTime != null && limitTime.after(endTime)) {
                    throw new BaseException("assign.due-time.after.end-time");
                }
            }

//            AssignTypeEnum assignType = AssignTypeEnum.typeOf(assign.getAssignType());
//            Section section = assignType == AssignTypeEnum.SECTION ? sectionMap.get(assign.getAssignId()) : null;
//            valid(assign, section, course, term);
            valid(assign, null, course, term);
        }
    }

    /**
     * 校验是否有重复分配的对象
     *
     * @param assigns
     */
    private void validAssignDuplicate(List<Assign> assigns) {
        Set<Long> assignSectionIds = new HashSet<>();
        Set<Long> assignUserIds = new HashSet<>();
        boolean hasAssignAll = false;

        for (Assign assign : assigns) {
            AssignTypeEnum assignType = AssignTypeEnum.typeOf(assign.getAssignType());
            if (assignType == null) {
                throw new BaseException("prop.value.not-exists","AssignType",assign.getAssignType()+"");
            }
            Long assignId = assign.getAssignId();
            switch (assignType) {
                case ALL:
                    if (hasAssignAll) {
                        throw new BaseException("assign.duplicate.for.all");
                    } else {
                        hasAssignAll = true;
                    }
                    break;
                case SECTION:
                    if (assignId == null) {
                        throw new BaseException("assign.section.id.null");
                    }

                    if (assignSectionIds.contains(assignId)) {
                        throw new BaseException("assign.duplicate.for.section", String.valueOf(assignId));
                    } else {
                        assignSectionIds.add(assignId);
                    }
                    break;
                case USER:
                    if (assignId == null) {
                        throw new BaseException("assign.user.id.null");
                    }

                    if (assignUserIds.contains(assignId)) {
                        throw new BaseException("assign.duplicate.for.user", String.valueOf(assignId));
                    } else {
                        assignUserIds.add(assignId);
                    }
                    break;
                default:
                    throw new BaseException("assign.type.unregistered", String.valueOf(assign.getAssignType()));
            }
        }
    }

    private void valid(Assign assign, Section section, Course course, Term term) {
        DateValid dateValid = new SectionDateValid(new CourseDateValid(new TermDateValid(new DefaultDateValid())));
        String errorKey = dateValid.valid(assign, section, course, term);
        if (errorKey != null) {
            throw new BaseException(errorKey);
        }
    }

    /**
     *
     */
    interface DateValid {
        default String valid(Assign assign, Section section, Course course, Term term) {
            return null;
        }
    }

    class DefaultDateValid implements DateValid {
    }

    class SectionDateValid implements DateValid {
        DateValid parent;

        public SectionDateValid(DateValid dateValid) {
            this.parent = dateValid == null ? new DefaultDateValid() : dateValid;
        }

        @Override
        public String valid(Assign assign, Section section, Course course, Term term) {
            if (section == null || (section.getStartTime() == null && section.getEndTime() == null)) {
                return parent.valid(assign, null, course, term);
            }

            Date sectionStartTime = section.getStartTime();
            Date sectionEndTime = section.getEndTime();
            Date assignStartTime = assign.getStartTime();
            Date assignEndTime = assign.getEndTime();
            Date assignLimitTime = assign.getLimitTime();

            if (sectionStartTime != null) {
                if (assignStartTime != null && assignStartTime.before(sectionStartTime)) {
                    return "assign.start-time.before.section-start-time";
                }
                if (assignStartTime == null && assignLimitTime != null && assignLimitTime.before(sectionStartTime)) {
                    return "assign.due-time.before.section-start-time";
                }
                if (assignStartTime == null && assignLimitTime == null
                        && assignEndTime != null && assignEndTime.before(sectionStartTime)) {
                    return "assign.end-time.before.section-start-time";
                }
            }
            if (sectionEndTime != null) {
                if (assignEndTime != null && assignEndTime.after(sectionEndTime)) {
                    return "assign.end-time.after.section-end-time";
                }
                if (assignEndTime == null && assignLimitTime != null && assignLimitTime.after(sectionEndTime)) {
                    return "assign.due-time.after.section-end-time";
                }
                if (assignEndTime == null && assignLimitTime == null
                        && assignStartTime != null && assignStartTime.after(sectionEndTime)) {
                    return "assign.start-time.after.section-end-time";
                }
            }

            return null;
        }

    }

    class CourseDateValid implements DateValid {
        DateValid parent;

        public CourseDateValid(DateValid dateValid) {
            this.parent = dateValid == null ? new DefaultDateValid() : dateValid;
        }

        @Override
        public String valid(Assign assign, Section section, Course course, Term term) {
            if (course == null || (course.getStartTime() == null && course.getEndTime() == null)) {
                return this.parent.valid(assign, section, course, term);
            }
            Date courseStartTime = course.getStartTime();
            Date courseEndTime = course.getEndTime();
            Date assignStartTime = assign.getStartTime();
            Date assignEndTime = assign.getEndTime();
            Date assignLimitTime = assign.getLimitTime();

            if (courseStartTime != null) {
                if (assignStartTime != null && assignStartTime.before(courseStartTime)) {
                    return "assign.start-time.before.course-start-time";
                }
                if (assignStartTime == null && assignLimitTime != null && assignLimitTime.before(courseStartTime)) {
                    return "assign.due-time.before.course-start-time";
                }
                if (assignStartTime == null && assignLimitTime == null
                        && assignEndTime != null && assignEndTime.before(courseStartTime)) {
                    return "assign.end-time.before.course-start-time";
                }
            }

            if (courseEndTime != null) {
                if (assignEndTime != null && assignEndTime.after(courseEndTime)) {
                    return "assign.end-time.after.course-end-time";
                }
                if (assignEndTime == null && assignLimitTime != null && assignLimitTime.after(courseEndTime)) {
                    return "assign.due-time.after.course-end-time";
                }
                if (assignEndTime == null && assignLimitTime == null
                        && assignStartTime != null && assignStartTime.after(courseEndTime)) {
                    return "assign.start-time.after.course-end-time";
                }
            }

            return null;
        }
    }

    class TermDateValid implements DateValid {
        DateValid parent;

        public TermDateValid(DateValid dateValid) {
            this.parent = dateValid == null ? new DefaultDateValid() : dateValid;
        }

        @Override
        public String valid(Assign assign, Section section, Course course, Term term) {
            if (term == null || (term.getStartTime() == null && term.getEndTime() == null)) {
                return this.parent.valid(assign, section, course, term);
            }
            Date termStartTime = term.getStartTime();
            Date termEndTime = term.getEndTime();
            Date assignStartTime = assign.getStartTime();
            Date assignEndTime = assign.getEndTime();
            Date assignLimitTime = assign.getLimitTime();

            if (termStartTime != null) {
                if (assignStartTime != null && assignStartTime.before(termStartTime)) {
                    return "assign.start-time.before.term-start-time";
                }
                if (assignStartTime == null && assignLimitTime != null && assignLimitTime.before(termStartTime)) {
                    return "assign.due-time.before.term-start-time";
                }
                if (assignStartTime == null && assignLimitTime == null
                        && assignEndTime != null && assignEndTime.before(termStartTime)) {
                    return "assign.end-time.before.term-start-time";
                }
            }

            if (termEndTime != null) {
                if (assignEndTime != null && assignEndTime.after(termEndTime)) {
                    return "assign.end-time.after.term-end-time";
                }
                if (assignEndTime == null && assignLimitTime != null && assignLimitTime.after(termEndTime)) {
                    return "assign.due-time.after.term-end-time";
                }
                if (assignEndTime == null && assignLimitTime == null
                        && assignStartTime != null && assignStartTime.after(termEndTime)) {
                    return "assign.start-time.after.term-end-time";
                }
            }

            return null;
        }
    }
}

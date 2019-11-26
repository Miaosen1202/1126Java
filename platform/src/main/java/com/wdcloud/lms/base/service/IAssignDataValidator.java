//package com.wdcloud.lms.base.service;
//
//import com.wdcloud.base.exception.BaseException;
//import com.wdcloud.lms.core.base.model.Assign;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public interface IAssignDataValidator {
//    boolean valid(Assign assign);
//}
//
//class AssignDataValidator implements IAssignDataValidator {
//    @Override
//    public boolean valid(Assign assign) {
//        Date startTime = assign.getStartTime();
//        Date endTime = assign.getEndTime();
//        Date limitTime = assign.getLimitTime();
//
//        if (startTime != null && endTime != null && startTime.after(endTime)) {
//            throw new BaseException("assign.start-time.after.end-time");
//        }
//
//        if (limitTime != null) {
//            if (startTime != null && limitTime.before(startTime)) {
//                throw new BaseException("assign.due-time.after.end-time");
//            }
//            if (endTime != null && limitTime.after(endTime)) {
//                throw new BaseException("assign.due-time.before.start-time");
//            }
//        }
//        return true;
//    }
//
//    public static void main(String[] args) {
//        List<Assign> assigns = new ArrayList<>();
//
//
//        TermAssignDataValidator termAssignDataValidator = new TermAssignDataValidator(new Date(), new Date());
//        CourseAssignDataValidator courseAssignDataValidator = new CourseAssignDataValidator(new Date(), new Date());
//
//        termAssignDataValidator.setAssignDataValidator(courseAssignDataValidator);
//
//        for (Assign assign : assigns) {
//            SectionAssignDataValidator sectionAssignDataValidator = new SectionAssignDataValidator(new Date(), new Date(), new AssignDataValidator());
//            courseAssignDataValidator.setAssignDataValidator(sectionAssignDataValidator);
//
//            termAssignDataValidator.valid(assign);
//        }
//    }
//}
//
//@Data
//@AllArgsConstructor
//class SectionAssignDataValidator implements IAssignDataValidator {
//    private Date sectionStartTime;
//    private Date sectionEndTime;
//
//    private IAssignDataValidator assignDataValidator;
//
//    public SectionAssignDataValidator(Date sectionStartTime, Date sectionEndTime) {
//        this.sectionStartTime = sectionStartTime;
//        this.sectionEndTime = sectionEndTime;
//    }
//
//    @Override
//    public boolean valid(Assign assign) {
//        boolean isContinue = assignDataValidator.valid(assign);
//        if (!isContinue) {
//            return false;
//        }
//
//        if (sectionStartTime != null) {
//            if (assign.getStartTime() != null && assign.getStartTime().before(sectionStartTime)) {
//                throw new BaseException("assign.start-time.before.section-start-time");
//            } else if (assign.getStartTime() == null && assign.getLimitTime() != null && assign.getLimitTime().before(sectionStartTime)) {
//                throw new BaseException("assign.due-time.before.section-start-time");
//            }
//        }
//
//        if (sectionEndTime != null) {
//            if (assign.getEndTime() != null && assign.getEndTime().after(sectionEndTime)) {
//                throw new BaseException("assign.start-time.after.section-end-time");
//            } else if (assign.getEndTime() == null && assign.getLimitTime() != null && assign.getLimitTime().after(sectionEndTime)) {
//                throw new BaseException("assign.due-time.after.section-end-time");
//            }
//        }
//
//        if (sectionStartTime != null && sectionEndTime != null) {
//            return false;
//        }
//
//        return true;
//    }
//}
//
//@Data
//@AllArgsConstructor
//class CourseAssignDataValidator implements IAssignDataValidator {
//    private Date courseStartTime;
//    private Date courseEndTime;
//    private IAssignDataValidator assignDataValidator;
//
//    public CourseAssignDataValidator(Date courseStartTime, Date courseEndTime) {
//        this.courseStartTime = courseStartTime;
//        this.courseEndTime = courseEndTime;
//    }
//
//    @Override
//    public boolean valid(Assign assign) {
//        boolean isContinue = assignDataValidator.valid(assign);
//        if (!isContinue) {
//            return false;
//        }
//
//        if (courseStartTime != null) {
//            if (assign.getStartTime() != null && assign.getStartTime().before(courseStartTime)) {
//                throw new BaseException("assign.start-time.before.course-start-time");
//            }
//            if (assign.getStartTime() == null && assign.getLimitTime() != null && assign.getLimitTime().before(courseStartTime)) {
//                throw new BaseException("assign.due-time.before.course-start-time");
//            }
//        }
//
//        if (courseEndTime != null) {
//            if (assign.getEndTime() != null && assign.getEndTime().after(courseEndTime)) {
//                throw new BaseException("assign.start-time.after.course-end-time");
//            }
//            if (assign.getEndTime() == null && assign.getLimitTime() != null && assign.getLimitTime().after(courseEndTime)) {
//                throw new BaseException("assign.due-time.after.course-end-time");
//            }
//        }
//
//        if (courseStartTime != null && courseEndTime != null) {
//            return false;
//        }
//
//        return true;
//    }
//}
//
//@Data
//@AllArgsConstructor
//class TermAssignDataValidator implements IAssignDataValidator {
//    private Date termStartTime;
//    private Date termEndTime;
//    private IAssignDataValidator assignDataValidator;
//
//    public TermAssignDataValidator(Date termStartTime, Date termEndTime) {
//        this.termStartTime = termStartTime;
//        this.termEndTime = termEndTime;
//    }
//
//    @Override
//    public boolean valid(Assign assign) {
//        boolean isContinue = assignDataValidator.valid(assign);
//        if (!isContinue) {
//            return false;
//        }
//
//        if (termStartTime != null) {
//            if (assign.getStartTime() != null && assign.getStartTime().before(termStartTime)) {
//                throw new BaseException("assign.start-time.before.term-start-time");
//            }
//            if (assign.getStartTime() == null && assign.getLimitTime() != null && assign.getLimitTime().before(termStartTime)) {
//                throw new BaseException("assign.start-time.after.term-end-time");
//            }
//        }
//
//        if (termEndTime != null) {
//            if (assign.getEndTime() != null && assign.getEndTime().after(termEndTime)) {
//                throw new BaseException("assign.due-time.before.term-start-time");
//            }
//            if (assign.getEndTime() == null && assign.getLimitTime() != null && assign.getLimitTime().after(termEndTime)) {
//                throw new BaseException("assign.due-time.after.term-end-time");
//            }
//        }
//
//        if (termStartTime != null && termEndTime != null) {
//            return false;
//        }
//
//        return true;
//    }
//}
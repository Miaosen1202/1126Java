package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceGradeEnum {

    KINDERGARTEN(0),

    FIRST_GRADE(1),

    SECOND_GRADE(2),

    THIRD_GRADE(3),

    FOURTH_GRADE(4),

    FIFTH_GRADE(5),

    SIXTH_GRADE(6),

    SEVENTH_GRADE(7),

    EIGHTH_GRADE(8),

    NINTH_GRADE(9),

    TENTH_GRADE(10),

    ELEVENTH_GRADE(11),

    TWELFTH_GRADE(12),

    UNDERGRADUATE(13),

    GRADUATE(14);

    private Integer grade;

    public static ResourceGradeEnum gradeOf(Integer grade){
        for(ResourceGradeEnum value : values()){
            if(Objects.equals(value.getGrade(), grade)){
                return value;
            }
        }
        return null;
    }
}

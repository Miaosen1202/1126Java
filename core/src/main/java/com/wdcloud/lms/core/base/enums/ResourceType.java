package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ResourceType {
    COURSE("res_type.course"),
    ASSIGNMENT("res_type.assignment"),
    DISCUSSION("res_type.discussion"),
    PAGE("res_type.page"),
    QUIZ("res_type.quiz"),
    MODULE("res_type.module"),
    DOCUMENT("res_type.document"),
    VIDEO("res_type.video"),
    AUDIO("res_type.audio"),
    IMAGE("res_type.image");


    private String type;

    public static ResourceType nameOf(String name) {
        for (ResourceType resourceType : values()) {
            if (Objects.equals(resourceType.name(), name)) {
                return resourceType;
            }
        }
        return null;
    }

    public static ResourceType typeOf(String type) {
        for (ResourceType resourceType : values()) {
            if (Objects.equals(resourceType.type, type)) {
                return resourceType;
            }
        }
        return null;
    }
}

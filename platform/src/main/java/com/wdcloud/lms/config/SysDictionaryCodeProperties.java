package com.wdcloud.lms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sys-dictionary")
public class SysDictionaryCodeProperties {
    private String courseNav;
    private String language;
}

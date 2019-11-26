package com.wdcloud.lms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "user-file")
public class UserFileProps {

    private DefaultName defaultName;


    private List<String> embedContentSuffixes;

    @Data
    public static class DefaultName {
        private String unfiledDir;
        private String submissionDir;
        private String profileDir;
        private String myFileDir;
        private String profilePicture;
    }
}

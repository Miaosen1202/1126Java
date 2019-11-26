package com.wdcloud.lms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseRegistryCodeGenerator {
    public static void main(String[] args) throws IOException {
        createLessonCode();
        byte[] word = new byte[]{'A', 'B'};
        System.out.println("" + String.valueOf(word[0]) + word[1]);
    }

    private static void createLessonCode() throws IOException {
        String[] words = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "J", "k", "L", "M", "N", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "2", "3", "4", "5", "6", "7", "8", "9"};

        List<String> codes = new ArrayList<>(4 ^ 36);
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                for (int k = 0; k < words.length; k++) {
                    for (int m = 0; m < words.length; m++) {
                        codes.add("'" + words[i] + words[j] + words[k] + words[m] + "'");
                    }
                }
            }
        }
        Collections.shuffle(codes);
        System.out.println(codes.size());

        StringBuilder ss = new StringBuilder("insert into cos_course_registry_code_bank(code) values ");
        for (String code : codes) {
            ss.append(" (")
                    .append(code)
                    .append("), \r\n");
        }
        ss.setLength(ss.length() - 1);
        ss.append(";");

        File file = new File("course-registry-code.sql");
        FileWriter writer = new FileWriter(file);
        writer.write(ss.toString());
    }

}

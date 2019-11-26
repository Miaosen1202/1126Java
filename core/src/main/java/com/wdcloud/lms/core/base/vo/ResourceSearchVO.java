package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Resource;
import lombok.Data;

import java.math.BigInteger;

@Data
public class ResourceSearchVO extends Resource {
    private String author;//作者
    private String thumbnailUrl;//缩略图

    public static void main(String[] args) {
       System.out.println(new BigInteger("14").toString(2));
       int grade = 8;
       int[] levels = {1, 3};
       for (int i = levels[0]; i <= levels[1]; i++) {
           int level = i;
           final int pow = (int) Math.pow(2, level);
            grade |= pow;
        }
        System.out.println("grade=" + grade);
        final int pow = (int) Math.pow(3, 5);
        System.out.println("pow: " + pow);
        grade|=pow;
        System.out.println(grade);
    }
}

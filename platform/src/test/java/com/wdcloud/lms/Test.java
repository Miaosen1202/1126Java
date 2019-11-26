package com.wdcloud.lms;

/**
 * @author wangff
 * @date 2019/9/24 15:17
 */
public class Test {
    public static void main(String[] args) {
        String a = "900.00";
        String a1 = "900";
        String b = a.split("\\.")[0];
        System.out.println(b);
    }
}

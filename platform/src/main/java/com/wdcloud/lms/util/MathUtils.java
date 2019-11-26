package com.wdcloud.lms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author wangff
 * @date 2019/9/26 15:50
 */
public class MathUtils {
    public static double formatDouble2(double d) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
//        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
//        return bg.doubleValue();
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(d));
    }

}

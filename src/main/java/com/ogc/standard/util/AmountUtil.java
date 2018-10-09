package com.ogc.standard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtil {
    public static Long mul(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return a.multiply(b).longValue();
    }

    public static Long mulJinFen(Long amount, double rate) {
        BigDecimal a = new BigDecimal(Double.toString(amount));
        BigDecimal b = new BigDecimal(Double.toString(rate));
        return rmbJinFen(a.multiply(b).doubleValue());
    }

    public static Long rmbJinFen(Double amount) {
        // 保留到分
        return Double.valueOf(Math.ceil(amount / 10) * 10).longValue();
    }

    public static double div(Long amount, double rate) {
        BigDecimal a = new BigDecimal(amount);
        BigDecimal b = new BigDecimal(rate);
        return a.divide(b, 2, RoundingMode.DOWN).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(mulJinFen(1000L, 0.980990909d));
    }
}

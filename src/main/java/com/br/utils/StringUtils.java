package com.br.utils;

import org.springframework.util.NumberUtils;

public class StringUtils {

    private StringUtils() { }

    public static Integer convertStringToNumber(String number) {
        return NumberUtils.parseNumber(number, Integer.class);
    }

    public static boolean validateString(String name) {
        return org.springframework.util.StringUtils.hasText(name);
    }
}

package ru.gubber.portfoliohistory.common.utils;

public class StringUtils {

    public static String cut(String str, int length) {
        if(str.length() <= length) {
            return str;
        }
        return str.substring(0, length);
    }
}

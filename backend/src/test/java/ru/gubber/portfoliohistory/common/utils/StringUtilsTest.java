package ru.gubber.portfoliohistory.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    @Test
    @DisplayName("При длине строки 8 символов и необходимой длине 1 вернуть длину 1 ")
    void cut_whenStringIsLonger1_thenReturnString1() {
        String string = StringUtils.cut("проверка", 1);
        int lengthResult = string.length();
        assertEquals(1, lengthResult);
    }

    @Test
    @DisplayName("При длине строки 8 символов и необходимой длине 5 вернуть длину 5 ")
    void cut_whenStringIsLonger_thenReturnString() {
        String string = StringUtils.cut("проверка", 5);
        int lengthResult = string.length();
        assertEquals(5, lengthResult);
    }

    @Test
    @DisplayName("При длине строки 1 символов и необходимой длине 5 вернуть исходную строку длиной 1 ")
    void cut_whenStringIsLess_thenReturnString() {
        String string = StringUtils.cut("п", 5);
        int lengthResult = string.length();
        assertEquals(1, lengthResult);
    }
}
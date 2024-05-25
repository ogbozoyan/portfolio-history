package ru.gubber.portfoliohistory.common.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    @ParameterizedTest(name = "Для строки {0} необходимо возвращать ошибку.")
    @NullSource
    @ValueSource(strings = { "", "                ", "\t" })
    public void validateStringField_shouldReturnCorrectError(String str) {
        FieldValidationError fieldValidationError = ValidationUtils.validateStringField(str);
        assertNotNull(fieldValidationError);
    }
}
package ru.gubber.portfoliohistory.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @ParameterizedTest(name = "Для строки {0} необходимо возвращать ошибку.")
    @NullSource
    @ValueSource(strings = { "", "                ", "\t" })
    void validateStringField_shouldReturnCorrectError(String str) {
        FieldValidationError fieldValidationError = ValidationUtils.validateStringField(str, "name");
        assertNotNull(fieldValidationError);
    }

    @Test
    @DisplayName("При непустой строке возвращается null")
    void validateStringField_whenStringNotEmpty_thenReturnNull() {
        FieldValidationError fieldValidationError = ValidationUtils.validateStringField("проверка", "name");
        assertNull(fieldValidationError);
    }

    @Test
    @DisplayName("При невалидном uuid возвращается ошибка")
    void validateUUID_whenUUIDIsInvalid_thenReturnFalse() {
        String invalid = "невалидный";
        Assertions.assertNotNull(ValidationUtils.validateUUID(invalid, "uuid"));
    }

    @Test
    @DisplayName("При валидном uuid возвращается null")
    void validateUUID_whenUUIDIsValid_thenReturnNull() {
        Pattern UUID_REGEX =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        String valid = "b1acc051-ea56-4ffc-a249-67010f8dd131";
        Assertions.assertNull(ValidationUtils.validateUUID(valid, "uuid"));
    }
}
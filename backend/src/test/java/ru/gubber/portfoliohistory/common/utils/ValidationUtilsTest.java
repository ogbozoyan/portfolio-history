package ru.gubber.portfoliohistory.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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
    void validateUuidField_whenUuidFieldIsInvalid_thenReturnFalse() {
        String invalid = "невалидный";
        Assertions.assertNotNull(ValidationUtils.validateUuidField(invalid, "id"));
    }

    @Test
    @DisplayName("При невалидном uuid - пустой строке - возвращается ошибка")
    void validateUuidField_whenUuidFieldIsEmptyString_thenReturnFalse() {
        String invalid = " ";
        Assertions.assertNotNull(ValidationUtils.validateUuidField(invalid, "id"));
    }

    @Test
    @DisplayName("При валидном uuid возвращается null")
    void validateUuidField_whenUuidFieldIsValid_thenReturnNull() {
        String valid = "b1acc051-ea56-4ffc-a249-67010f8dd131";
        Assertions.assertNull(ValidationUtils.validateUuidField(valid, "id"));
    }
}
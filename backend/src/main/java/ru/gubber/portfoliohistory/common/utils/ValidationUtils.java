package ru.gubber.portfoliohistory.common.utils;

public class ValidationUtils {

    public static FieldValidationError validateStringField(String s, String name) {
        if (s==null || s.isEmpty() || s.isBlank()) {
            return new FieldValidationError(name, "Поле не может быть пустым.");
        } else {
            return null;
        }
    }

    public static FieldValidationError validateNumberField(Number number, String s) {
        if (number==null) {
            return new FieldValidationError(s, "Поле не может быть пустым.");
        } else {
            return null;
        }
    }
}

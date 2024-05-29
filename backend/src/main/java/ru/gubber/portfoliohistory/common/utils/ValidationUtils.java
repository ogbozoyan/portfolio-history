package ru.gubber.portfoliohistory.common.utils;

public class ValidationUtils {

    public static FieldValidationError validateStringField(String s) {
        if (s==null || s.isEmpty() || s.isBlank()) {
            return new FieldValidationError("\"" + s + "\"", "Поле не может быть пустым.");
        } else {
            return null;
        }
    }

    public static FieldValidationError validateNumberField(Number s) {
        if (s==null) {
            return new FieldValidationError("\"" + s + "\"", "Поле не может быть пустым.");
        } else {
            return null;
        }
    }
}

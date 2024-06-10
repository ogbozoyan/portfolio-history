package ru.gubber.portfoliohistory.common.utils;

import java.util.regex.Pattern;

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

    public static boolean validateUUID(String uuid) {
        Pattern UUID_REGEX =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return UUID_REGEX.matcher(uuid).matches();
    }
}

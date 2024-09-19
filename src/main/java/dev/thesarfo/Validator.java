package dev.thesarfo;

import java.lang.reflect.Field;

public class Validator {

    public static void validatePhoneNumbers(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(PhoneNumber.class)) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value instanceof String) {
                    String phoneNumber = (String) value;
                    if (!isValidPhoneNumber(phoneNumber)) {
                        throw new IllegalArgumentException(
                                "Invalid phone number format: " + phoneNumber);
                    }
                } else {
                    throw new IllegalStateException(
                            "@PhoneNumber is only applicable String fields.");
                }
            }
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}");
    }
}


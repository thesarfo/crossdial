package dev.thesarfo.library;

import dev.thesarfo.annotations.Inspect;
import dev.thesarfo.validators.Inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DoomsDay {

    public static void validate(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        List<String> errorMessages = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);

            for (Annotation annotation : field.getAnnotations()) {
                Inspect validate = extractValidateAnnotation(annotation);
                if (validate != null) {
                    String errorMessage = validateField(validate, field, value, annotation);
                    if (errorMessage != null) {
                        errorMessages.add(errorMessage);
                    }
                }
            }
        }

        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException("Validation failed:\n" + String.join("\n", errorMessages));
        }
    }

    private static Inspect extractValidateAnnotation(Annotation annotation) {
        if (annotation instanceof Inspect) {
            return (Inspect) annotation;
        }
        return annotation.annotationType().getAnnotation(Inspect.class);
    }

    private static String validateField(Inspect validate, Field field, Object value, Annotation annotation) {
        try {
            Inspector<?> validator = validate.inspector().getDeclaredConstructor().newInstance();
            @SuppressWarnings("unchecked")
            boolean isValid = ((Inspector<Object>) validator).isValid(value);
            if (!isValid) {
                String message = (String) annotation.annotationType().getMethod("message").invoke(annotation);
                return message + " Field: " + field.getName();
            }
        } catch (Exception e) {
            return "Error validating field: " + field.getName() + ". " + e.getMessage();
        }
        return null;
    }
}
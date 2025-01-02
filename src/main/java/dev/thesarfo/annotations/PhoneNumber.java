package dev.thesarfo.annotations;

import dev.thesarfo.validators.PhoneNumberInspector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inspect(inspector = PhoneNumberInspector.class, message = "Invalid phone number.")
public @interface PhoneNumber {
    String message() default "Invalid phone number.";
}
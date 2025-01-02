package dev.thesarfo.annotations;


import dev.thesarfo.validators.Inspector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Inspect {
    Class<? extends Inspector<?>> inspector();
    String message() default "Validation failed.";
}



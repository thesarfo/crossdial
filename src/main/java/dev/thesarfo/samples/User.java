package dev.thesarfo;



import dev.thesarfo.annotations.Validate;
import dev.thesarfo.validators.AgeValidator;
import dev.thesarfo.validators.PhoneNumberValidator;

public class User {

    @Validate(validator = PhoneNumberValidator.class, message = "Invalid phone number format.")
    private String phoneNumber;

    @Validate(validator = AgeValidator.class, message = "Age must be between 18 and 120.")
    private int age;

    public User(String phoneNumber, int age) {
        this.phoneNumber = phoneNumber;
        this.age = age;
    }
}

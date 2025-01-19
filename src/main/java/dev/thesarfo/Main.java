package dev.thesarfo;

import dev.thesarfo.validators.PhoneNumberValidator;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize validator
        PhoneNumberValidator validator = new PhoneNumberValidator();

        // Example 1: Single number validation
        PhoneNumberValidator.ValidationResult result = validator.validateNumber("+233244444444", "GH");
        if (result.isValid()) {
            String formattedNumber = result.getFormattedNumber();
            System.out.println("Valid number: " + formattedNumber);
        } else {
            PhoneNumberValidator.ValidationError error = result.getError();
            System.out.println("Invalid number: " + error.getMessage());
        }

        // Example 2: Batch validation
        List<PhoneNumberValidator.PhoneNumberRequest> numbers = Arrays.asList(
                new PhoneNumberValidator.PhoneNumberRequest("+233244444444", "GH"),
                new PhoneNumberValidator.PhoneNumberRequest("+1234567890", "US")
        );

        List<PhoneNumberValidator.ValidationResult> results = validator.validateBatch(numbers);
        results.forEach(r -> {
            if (r.isValid()) {
                System.out.println("Valid: " + r.getFormattedNumber());
            } else {
                System.out.println("Invalid: " + r.getOriginalNumber() +
                        " - " + r.getError().getMessage());
            }
        });

        // Example 3: Get supported countries
        List<PhoneNumberValidator.CountryCode> countries = validator.getSupportedCountries();
        countries.forEach(c ->
                System.out.println(c.getCountryName() + ": " + c.getCode())
        );
    }
}

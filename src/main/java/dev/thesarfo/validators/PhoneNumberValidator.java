package dev.thesarfo.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PhoneNumberValidator {
    private final PhoneNumberUtil phoneNumberUtil;

    public PhoneNumberValidator() {
        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
    }

    /**
     * Represents the result of phone number validation
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final String formattedNumber;
        private final String originalNumber;
        private final ValidationError error;

        public ValidationResult(boolean isValid, String formattedNumber, String originalNumber, ValidationError error) {
            this.isValid = isValid;
            this.formattedNumber = formattedNumber;
            this.originalNumber = originalNumber;
            this.error = error;
        }

        public boolean isValid() { return isValid; }
        public String getFormattedNumber() { return formattedNumber; }
        public String getOriginalNumber() { return originalNumber; }
        public ValidationError getError() { return error; }
    }

    /**
     * Represents different types of validation errors
     */
    public enum ValidationError {
        INVALID_FORMAT("Invalid phone number format"),
        INVALID_COUNTRY_CODE("Invalid country code"),
        NUMBER_TOO_SHORT("Number is too short"),
        NUMBER_TOO_LONG("Number is too long"),
        EMPTY_NUMBER("Phone number cannot be empty"),
        PARSE_ERROR("Could not parse phone number");

        private final String message;

        ValidationError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Validates a single phone number for a specific country
     * @param phoneNumber The phone number to validate
     * @param countryCode The ISO 3166-1 alpha-2 country code
     * @return ValidationResult containing the validation status and any errors
     */
    public ValidationResult validateNumber(String phoneNumber, String countryCode) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return new ValidationResult(false, null, phoneNumber, ValidationError.EMPTY_NUMBER);
        }

        if (!isValidCountryCode(countryCode)) {
            return new ValidationResult(false, null, phoneNumber, ValidationError.INVALID_COUNTRY_CODE);
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, countryCode);

            if (!phoneNumberUtil.isValidNumber(number)) {
                return new ValidationResult(false, null, phoneNumber, ValidationError.INVALID_FORMAT);
            }

            String formatted = phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            return new ValidationResult(true, formatted, phoneNumber, null);

        } catch (NumberParseException e) {
            ValidationError error = switch (e.getErrorType()) {
                case TOO_SHORT_NSN -> ValidationError.NUMBER_TOO_SHORT;
                case TOO_LONG -> ValidationError.NUMBER_TOO_LONG;
                default -> ValidationError.PARSE_ERROR;
            };
            return new ValidationResult(false, null, phoneNumber, error);
        }
    }

    /**
     * Validates multiple phone numbers with their respective country codes
     * @param numbers List of phone numbers with country codes to validate
     * @return List of validation results
     */
    public List<ValidationResult> validateBatch(List<PhoneNumberRequest> numbers) {
        return numbers.stream()
                .map(req -> validateNumber(req.getPhoneNumber(), req.getCountryCode()))
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of all supported country codes and their names
     * @return List of CountryCode objects
     */
    public List<CountryCode> getSupportedCountries() {
        return phoneNumberUtil.getSupportedRegions().stream()
                .map(region -> new CountryCode(
                        new Locale("", region).getDisplayCountry(),
                        region
                ))
                .collect(Collectors.toList());
    }

    public static class CountryCode {
        private final String countryName;
        private final String code;

        public CountryCode(String countryName, String code) {
            this.countryName = countryName;
            this.code = code;
        }

        public String getCountryName() { return countryName; }
        public String getCode() { return code; }
    }

    public static class PhoneNumberRequest {
        private final String phoneNumber;
        private final String countryCode;

        public PhoneNumberRequest(String phoneNumber, String countryCode) {
            this.phoneNumber = phoneNumber;
            this.countryCode = countryCode;
        }

        public String getPhoneNumber() { return phoneNumber; }
        public String getCountryCode() { return countryCode; }
    }

    /**
     * Checks if the given country code is valid and supported
     * @param countryCode The ISO 3166-1 alpha-2 country code to validate
     * @return boolean indicating if the country code is valid and supported
     */
    public boolean isValidCountryCode(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            return false;
        }
        String normalizedCode = countryCode.trim().toUpperCase();
        return phoneNumberUtil.getSupportedRegions().contains(normalizedCode);
    }
}
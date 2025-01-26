# CrossDial: Phone Number Validator

CrossDial is a library for validating and formatting international phone numbers. It provides an easy-to-use interface for phone number validation with full error handling and batch support.

![License](https://img.shields.io/badge/license-MIT-blue.svg)  ![Maven](https://img.shields.io/maven-central/v/io.github.thesarfo/crossdial?label=Maven%20Central)

## Table of Contents
1. [What it Does](#what-it-does)
2. [Getting Started](#getting-started)
3. [Usage](#usage)
   - [Validate a Single Phone Number](#validate-a-single-phone-number)
   - [Validate Multiple Numbers (Batch Validation)](#validate-multiple-numbers-batch-validation)
   - [List Supported Countries](#list-supported-countries)
4. [Understanding Validation Results](#understanding-validation-results)
5. [Common Error Types](#common-error-types)
6. [Best Practices](#best-practices)
7. [Examples](#examples)
   - [Valid Number Formats](#valid-number-formats)
   - [Error Handling Example](#error-handling-example)
8. [Thread Safety](#thread-safety)
9. [Contributing](#contributing)
10. [Acknowledgements](#acknowledgements)

---

## What it Does

- **Phone Number Validation**: Validates individual phone numbers.
- **Batch Validation**: Supports validating multiple phone numbers at once.
- **Phone Number Formatting**: Formats phone numbers according to the E.164 standard.
- **Detailed Error Reporting**: Provides clear error messages for invalid numbers.
- **Country Code Validation**: Ensures the country code matches the phone number format.
- **Thread-Safe**: Designed to be thread-safe for use in multi-threaded environments.
- **Error Handling**: Handles errors using `ValidationResult`—no runtime exceptions.

---

## Getting Started

#### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.thesarfo</groupId>
    <artifactId>crossdial</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### Gradle

Add this line to your `build.gradle`:

```gradle
implementation group: 'io.github.thesarfo', name: 'crossdial', version: '1.0.0'
```

---

## Usage

### Validate a Single Phone Number

```java
PhoneNumberValidator validator = new PhoneNumberValidator();

ValidationResult result = validator.validateNumber("+233244444444", "GH");

if (result.isValid()) {
    System.out.println("Valid: " + result.getFormattedNumber());
} else {
    System.out.println("Invalid: " + result.getError().getMessage());
}
```

### Validate Multiple Numbers (Batch Validation)

```java
List<PhoneNumberRequest> numbers = Arrays.asList(
    new PhoneNumberRequest("+233244444444", "GH"),
    new PhoneNumberRequest("+1234567890", "US")
);

List<ValidationResult> results = validator.validateBatch(numbers);

results.forEach(r -> {
    if (r.isValid()) {
        System.out.println("Valid: " + r.getFormattedNumber());
    } else {
        System.out.println("Invalid: " + r.getOriginalNumber() + " - " + r.getError().getMessage());
    }
});
```

### List Supported Countries

```java
List<CountryCode> countries = validator.getSupportedCountries();
countries.forEach(c -> 
    System.out.println(c.getCountryName() + ": " + c.getCode())
);
```

---

## Understanding Validation Results

When you validate a phone number, you receive a `ValidationResult` object. Here's how to use it:

- `isValid()`: Returns `true` if the phone number is valid.
- `getFormattedNumber()`: Returns the phone number formatted according to the E.164 standard.
- `getOriginalNumber()`: The phone number that was originally provided.
- `getError()`: If the number is invalid, this contains the error message.

---

## Common Error Types

The possible validation errors are encapsulated in an enum:

```java
public enum ValidationError {
    INVALID_FORMAT("Invalid phone number format"),
    INVALID_COUNTRY_CODE("Invalid country code"),
    NUMBER_TOO_SHORT("Number is too short"),
    NUMBER_TOO_LONG("Number is too long"),
    EMPTY_NUMBER("Phone number cannot be empty"),
    PARSE_ERROR("Could not parse phone number")
}
```

---

## Best Practices

- **Country Codes**: Always use ISO 3166-1 alpha-2 country codes (e.g., "US", "GB", "GH").
- **Phone Numbers**: Numbers can include or exclude the '+' prefix, spaces, or dashes as necessary. The library will clean up the formatting.

---

## Examples

### Valid Number Formats

```java
validator.validateNumber("+1234567890", "US");
validator.validateNumber("123-456-7890", "US");
```


### Error Handling Example

```java
ValidationResult result = validator.validateNumber("123", "US");
if (!result.isValid()) {
    switch (result.getError()) {
        case NUMBER_TOO_SHORT -> System.out.println("Please enter a complete number");
        case INVALID_COUNTRY_CODE -> System.out.println("Invalid country code");
        default -> System.out.println("Error: " + result.getError().getMessage());
    }
}
```

---

## Thread Safety

CrossDial is thread-safe, so you can use it safely across multiple threads or as a singleton in your applications:

```java
@Service
public class PhoneValidationService {
    private final PhoneNumberValidator validator = new PhoneNumberValidator();
    
    // Use the validator in your service methods
}
```

---

## Contributing

This project is meant to fit a very specific use case, but contributions are welcome. Feel free to open an issue or submit a pull request, or you can fork and build your own version

---

## Acknowledgements

This project was built on top of [Google's libphonenumber](https://github.com/google/libphonenumber).

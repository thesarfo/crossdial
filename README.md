## CrossDial: Phone Number Validator

CrossDial is a lightweight Java library for validating and formatting international phone numbers. It provides an easy-to-use interface for phone number validation with full error handling and batch support.

### What it does:

- Single phone number validation
- Batch validation support
- E.164 international phone number formatting
- Detailed error reporting
- Country code validation
- Thread-safe operations
- No runtime exceptions—errors are handled via `ValidationResult`

## Installation

### Maven

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.thesarfo</groupId>
    <artifactId>crossdial</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Usage

#### Basic Validation

```java
PhoneNumberValidator validator = new PhoneNumberValidator();
ValidationResult result = validator.validateNumber("+233244444444", "GH");

if (result.isValid()) {
    System.out.println("Valid: " + result.getFormattedNumber());
} else {
    System.out.println("Invalid: " + result.getError().getMessage());
}
```

#### Batch Validation

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

#### Get Supported Countries

```java
List<CountryCode> countries = validator.getSupportedCountries();
countries.forEach(c -> 
    System.out.println(c.getCountryName() + ": " + c.getCode())
);
```

---

### Validation Results

The `ValidationResult` object contains:

- `isValid()`: Whether the number is valid
- `getFormattedNumber()`: The phone number in E.164 format
- `getOriginalNumber()`: The input phone number
- `getError()`: The error message (if invalid)

---

### Error Types

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

### Best Practices

- **Country Codes**: Use ISO 3166-1 alpha-2 country codes (e.g., "US", "GB", "GH").
- **Phone Numbers**: Can include or exclude the '+' prefix, spaces, or dashes.

---

### Examples

### Valid Number Formats

```java
validator.validateNumber("+1234567890", "US");
validator.validateNumber("123-456-7890", "US");
```

### Error Handling

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

### Thread Safety

The validator is thread-safe and can be used as a singleton:

```java
@Service
public class PhoneValidationService {
    private final PhoneNumberValidator validator = new PhoneNumberValidator();
    
    // Use the validator in your methods
}
```

---

### Contributing

This project is meant to fit a specific use case, but contributions are welcome. Feel free to open an issue or submit a pull request, or you can fork and build your own version

### License

MIT License. See the LICENSE file for details.
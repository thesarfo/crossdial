
> This is a work in progress.

Lightweight Java library for validating objects using annotations.


#### Using the `@Inspect` Annotation Directly

You can use the `@Inspect` annotation directly on fields to specify the `Inspector` implementation and the custom validation message.

#### Example

```java
class User {

    @Inspect(inspector = PhoneNumberInspector.class, message = "Please provide a valid phone number.")
    private String phoneNumber;
}
```

#### Using Provided Custom Annotations

You can use the provided custom annotations that internally use the `@Inspect` annotation.

#### Example

```java
public class User {

    @PhoneNumber(message = "Please provide a valid phone number.")
    private String phoneNumber;
}
```

Then validate the object using the `Validator` class.

#### Example

```java
public class Main {
    public static void main(String[] args) {
        User user = new User("invalid-phone-number");

        try {
            DoomsDay.validate(user);
            System.out.println("Validation passed.");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }
}
```
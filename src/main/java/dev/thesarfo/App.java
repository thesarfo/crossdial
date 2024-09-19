package dev.thesarfo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        User user = new User("+233500918498");
        try {
            Validator.validatePhoneNumbers(user);
            System.out.println("Phone number is valid.");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }
}

package dev.thesarfo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        User user = new User("+233500918498");
        User user1 = new User("0209123456");
        try {
            Validator.validatePhoneNumbers(user);
            Validator.validatePhoneNumbers(user1);
            System.out.println("Phone number is valid.");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }
}

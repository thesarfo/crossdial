package dev.thesarfo;

import dev.thesarfo.library.DoomsDay;
import dev.thesarfo.samples.User;

public class App {
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

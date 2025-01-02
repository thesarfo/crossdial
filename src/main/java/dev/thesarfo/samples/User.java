package dev.thesarfo.samples;

import dev.thesarfo.annotations.PhoneNumber;

public class User {

    @PhoneNumber
    private String phoneNumber;

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

package dev.thesarfo;


public class User {

    @PhoneNumber
    private String phoneNumber;

//    @PhoneNumber
//    private int testing;

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }
}

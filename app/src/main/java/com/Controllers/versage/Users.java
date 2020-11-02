package com.Controllers.versage;

public class Users {
    private String username;
    private String email;
    private String PhoneNumber;

    public Users(String username, String email, String phoneNumber) {
        this.username = username;
        this.email = email;
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}

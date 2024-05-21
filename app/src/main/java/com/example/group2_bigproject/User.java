package com.example.group2_bigproject;

public class User {
    public String username;
    public String email;
    public String password;
    public String name;
    public String gender;
    public String birthday;
    public String phone;
    public String address;
    public String height;
    public String weight;

    public User() {}
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = "Name";
        this.gender = "Gender";
        this.birthday = "Birthday";
        this.phone = "0123456789";
        this.address = "address";
        this.height = "180";
        this.weight = "80";
    }
}

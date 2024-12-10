package com.example.easymeet.model;

public class User {


    private int Id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password; // Consider storing a hashed password instead


    // Constructor
    public User(String firstName, String lastName, String username, String email, String password, String confirmPassowrd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

}


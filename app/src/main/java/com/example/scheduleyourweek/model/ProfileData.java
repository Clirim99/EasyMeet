package com.example.scheduleyourweek.model;

public class ProfileData {
    private int Id; // Renamed from userId to match the table's primary key
    private String username;
        private String profilePic;
    private String description;

    // Constructor
    public ProfileData(int Id, String username, String profilePic, String description) {
        this.Id = Id;
        this.username = username;
        this.profilePic = profilePic;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

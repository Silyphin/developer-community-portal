package org.example.DSE105_Assignment_2.Model;

import java.time.LocalDateTime;

public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String company;
    private String city;
    private String country;
    private String phoneNumber;
    private String programmingLanguages;
    private String experience;
    private String bio;
    private String profilePicture;
    private boolean termsAccepted;

    // Default constructor
    public RegistrationForm() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    // Convert to User object
    public User toUser() {
        // Use the constructor with essential fields
        User user = new User(firstName, lastName, email, password);
        user.setCompany(this.company);
        user.setCity(this.city);
        user.setCountry(this.country);
        user.setPhoneNumber(this.phoneNumber);
        user.setProgrammingLanguages(this.programmingLanguages);
        user.setExperience(this.experience);
        user.setBio(this.bio);
        user.setProfilePicture(this.profilePicture);
        user.setRegistrationDate(LocalDateTime.now()); // Updated to LocalDateTime
        return user;
    }
}
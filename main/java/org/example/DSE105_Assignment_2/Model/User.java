package org.example.DSE105_Assignment_2.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "company")
    private String company;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "programming_languages")
    private String programmingLanguages;

    @Column(name = "experience")
    private String experience;

    @Column(name = "bio")
    private String bio;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "role")
    private String role;

    // New field for visibility
    @Column(name = "is_public")
    private boolean isPublic = true; // Default to public

    // Default constructor for JPA
    public User() {
        this.registrationDate = LocalDateTime.now();
        this.isAdmin = false;
        this.role = "user";
        this.isPublic = true; // Ensure default in constructor
    }

    // Constructor with essential fields
    public User(String firstName, String lastName, String email, String password) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Full constructor
    public User(Long id, String firstName, String lastName, String email, String password,
                String company, String city, String country, String phoneNumber,
                String programmingLanguages, String experience, String bio, String profilePicture) {
        this(firstName, lastName, email, password);
        this.id = id;
        this.company = company;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.programmingLanguages = programmingLanguages;
        this.experience = experience;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
        this.role = admin ? "admin" : "user";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        if ("admin".equalsIgnoreCase(role)) {
            this.isAdmin = true;
        } else {
            this.isAdmin = false;
        }
    }

    // Getter and setter for new field
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
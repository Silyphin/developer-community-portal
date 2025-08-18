package org.example.DSE105_Assignment_2.Model;

public class SearchForm {
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String country;
    private String programmingLanguage;

    // Default constructor
    public SearchForm() {
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

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    // Check if any search criteria is provided
    public boolean hasSearchCriteria() {
        return (firstName != null && !firstName.isEmpty()) ||
                (lastName != null && !lastName.isEmpty()) ||
                (company != null && !company.isEmpty()) ||
                (city != null && !city.isEmpty()) ||
                (country != null && !country.isEmpty()) ||
                (programmingLanguage != null && !programmingLanguage.isEmpty());
    }
}
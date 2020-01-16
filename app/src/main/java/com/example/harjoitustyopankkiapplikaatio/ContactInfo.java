package com.example.harjoitustyopankkiapplikaatio;

public class ContactInfo {


    private Integer id;
    private String firstName;
    private String surName;
    private String birthDate;
    private String email;
    private String phone ;
    private String streetName;
    private String city;
    private String postalCode;


    ContactInfo(int idTemp, String firstNameTemp, String surNameTemp, String birthDateTemp, String emailTemp, String phoneTemp, String streetNameTemp, String cityTemp, String postalCodeTemp){

        this.id = idTemp;
        this.firstName = firstNameTemp;
        this.surName =surNameTemp ;
        this.birthDate = birthDateTemp;
        this.email = emailTemp;
        this.phone =phoneTemp ;
        this.streetName = streetNameTemp;
        this.city = cityTemp;
        this.postalCode = postalCodeTemp;

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName(){return surName;}

    public String getStreetName() {
        return streetName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }


    public void setFirstName(String featureTextTemp) {
        firstName =featureTextTemp;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}

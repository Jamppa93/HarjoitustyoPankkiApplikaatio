package com.example.harjoitustyopankkiapplikaatio;

public class ContactInfo {


    Integer id;
    String firstName;
    String surName;
    String birthDate;
    String email;
    String phone ;
    String streetName;
    String city;
    String postalCode;


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




}

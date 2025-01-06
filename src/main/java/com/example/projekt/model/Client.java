package com.example.projekt.model;

import java.time.LocalDate;

public class Client {

    private String id;
    private String lastname;
    private String firstname;
    private LocalDate birthdate;
    private String gender;
    private String nationality;
    private String relationship;

    public Client(String id, String lastname, String firstname, LocalDate birthdate, String gender, String nation, String relationship) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.nationality = nation;
        this.relationship = relationship;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
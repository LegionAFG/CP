package com.example.projekt.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private String id;
    private LocalDate date;
    private LocalTime time;
    private String institution;
    private String postCode;
    private String street;
    private String status;
    private String lastname;
    private String firstname;

    public Appointment(String id,
                       String lastname,
                        String firstname,
                       LocalDate date,
                       LocalTime time,
                       String institution,
                       String postCode,
                       String street,
                       String status
                      )
    {
        this.id = id;
        this.date = date;
        this.time = time;
        this.institution = institution;
        this.postCode = postCode;
        this.street = street;
        this.status = status;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    // Getter & Setter

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
}

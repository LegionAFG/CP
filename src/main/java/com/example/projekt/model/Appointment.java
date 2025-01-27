package com.example.projekt.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private String id;
    private int appointmentId;
    private LocalDate date;
    private LocalTime time;
    private String institution;
    private String city;
    private String street;
    private String status;
    private String lastname;
    private String firstname;

    public Appointment(String id,int appointmentId,
                       String lastname,
                        String firstname,
                       LocalDate date,
                       LocalTime time,
                       String institution,
                       String city,
                       String street,
                       String status
                      )
    {
        this.id = id;
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.institution = institution;
        this.city = city;
        this.street = street;
        this.status = status;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Appointment(String id,
                       int appointmentId,
                       LocalDate date,
                       LocalTime time,
                       String institution,
                       String city,
                       String street,
                       String status
    )
    {
        this.id = id;
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.institution = institution;
        this.city = city;
        this.street = street;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(String id) {
        this.appointmentId = appointmentId;
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

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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

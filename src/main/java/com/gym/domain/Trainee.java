package com.gym.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;

    public Trainee() {
        super();
    }

    public Trainee(Long userId,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   LocalDate dateOfBirth,
                   String address) {
        super(userId, firstName, lastName, username, password);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Trainee trainee = (Trainee) object;
        return Objects.equals(dateOfBirth, trainee.dateOfBirth) && Objects.equals(address, trainee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfBirth, address);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                super.toString() +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
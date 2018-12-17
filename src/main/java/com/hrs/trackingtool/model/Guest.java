package com.hrs.trackingtool.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

//Data Access Object of model Guest
@Entity
@Table(
        name="guests",
        uniqueConstraints = @UniqueConstraint(name="uc_identity",columnNames = {"firstName","lastName","roomNumber"})
)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message="Check in date can not be null.")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkIN;


    @Column(nullable = false)
    @NotNull(message="Check out date can not be null.")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkOUT;

    @Column(nullable=false)
    @NotNull(message="First name can not be null.")
    private String firstName;


    @Column(nullable=false)
    @NotNull(message="Last name can not be null.")
    private String lastName;

    @Column(nullable=false)
    @NotNull(message="Room number can not be null.")
    private String roomNumber;

    public Guest() {
    }

    public Guest( String firstName, String lastName,  String roomNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
    }

    public Guest(LocalDateTime checkIN, LocalDateTime checkOUT, String firstName, String lastName, String roomNumber) {
        this.checkIN = checkIN;
        this.checkOUT = checkOUT;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIN() {
        return checkIN;
    }

    public void setCheckIN(LocalDateTime checkIN) {
        this.checkIN = checkIN;
    }

    public LocalDateTime getCheckOUT() {
        return checkOUT;
    }

    public void setCheckOUT(LocalDateTime checkOUT) {
        this.checkOUT = checkOUT;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;
        return Objects.equals(getId(), guest.getId()) &&
                Objects.equals(getCheckIN(), guest.getCheckIN()) &&
                Objects.equals(getCheckOUT(), guest.getCheckOUT()) &&
                Objects.equals(getFirstName(), guest.getFirstName()) &&
                Objects.equals(getLastName(), guest.getLastName()) &&
                Objects.equals(getRoomNumber(), guest.getRoomNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFirstName(),getLastName(),getRoomNumber(),getCheckIN(),getCheckOUT());
    }
}

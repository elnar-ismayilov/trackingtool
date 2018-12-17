package com.hrs.trackingtool.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

// Guest data transfer object that carries data between processes

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message="Check in date can not be null.")
    private LocalDateTime checkIN;

    @NotNull(message="Check out date can not be null.")
    private LocalDateTime checkOUT;

    @NotNull(message="First name can not be null.")
    private String firstName;

    @NotNull(message="Last name can not be null.")
    private String lastName;

    @NotNull(message="Room number can not be null.")
    private String roomNumber;

    public GuestDTO() {
    }

    public GuestDTO(Long id, LocalDateTime checkIN,  LocalDateTime checkOUT,  String firstName,  String lastName, String roomNumber) {
        this.id = id;
        this.checkIN = checkIN;
        this.checkOUT = checkOUT;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
    }

    public GuestDTO( String firstName,  String lastName,  String roomNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCheckIN() {
        return checkIN;
    }

    public void setCheckIN(LocalDateTime checkIN) {
        this.checkIN = checkIN;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

        GuestDTO guestDTO = (GuestDTO) o;
        return Objects.equals(getId(),guestDTO.getId())&&
                Objects.equals(getCheckIN(),guestDTO.getCheckIN())&&
                Objects.equals(getCheckOUT(),guestDTO.getCheckOUT())&&
                Objects.equals(getFirstName(),guestDTO.getFirstName())&&
                Objects.equals(getLastName(),guestDTO.getLastName())&&
                Objects.equals(getRoomNumber(),guestDTO.getRoomNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFirstName(),getLastName(),getRoomNumber(),getCheckIN(),getCheckOUT());
    }
}

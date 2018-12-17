package com.hrs.trackingtool.dto;

import com.fasterxml.jackson.annotation.*;
import com.hrs.trackingtool.modelvalue.ParcelType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Parcel data transfer object that carries data between processes
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParcelDTO {
    @JsonIgnore
    private Long id;

    @NotNull(message="Tracking number can not be null.")
    private String trackingNumber;

    @NotNull(message="Parcel type can not be null.")
    private ParcelType parcelType;

    private LocalDateTime  dateEntered;

    private Boolean isPickedUp;

    private GuestDTO guestDTO;

    public ParcelDTO() {
    }


    public ParcelDTO(Long id, String trackingNumber, GuestDTO guestDTO,  ParcelType parcelType,LocalDateTime dateEntered,  Boolean isPickedUp) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.guestDTO = guestDTO;
        this.parcelType = parcelType;
        this.dateEntered = dateEntered;
        this.isPickedUp = isPickedUp;
    }



    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ParcelType getParcelType() {
        return parcelType;
    }

    public void setParcelType(ParcelType parcelType) {
        this.parcelType = parcelType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime  getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(LocalDateTime  dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Boolean getPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(Boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public GuestDTO getGuestDTO() {
        return guestDTO;
    }

    public void setGuestDTO(GuestDTO guestDTO) {
        this.guestDTO = guestDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelDTO parcelDTO = (ParcelDTO) o;
        return Objects.equals(getId(), parcelDTO.getId()) &&
                Objects.equals(getTrackingNumber(), parcelDTO.getTrackingNumber()) &&
                Objects.equals(getDateEntered(), parcelDTO.getDateEntered()) &&
                Objects.equals(getPickedUp(),parcelDTO.getPickedUp()) &&
                getParcelType()==parcelDTO.getParcelType() &&
                Objects.equals(getGuestDTO(),parcelDTO.getGuestDTO());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrackingNumber(),getDateEntered(),getPickedUp(),getParcelType(),getGuestDTO());
    }
}

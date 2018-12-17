package com.hrs.trackingtool.model;

import com.hrs.trackingtool.modelvalue.ParcelType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

//Data Access Object of model Parcel
@Entity
@Table(
        name="parcels",
        uniqueConstraints = @UniqueConstraint(name="uc_tracking_number",columnNames = {"trackingNumber"})
)
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message="Enter date can not be null.")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateEntered;

    @Column(nullable=false)
    @NotNull(message="Tracking number can not be null.")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message="Parcel type can not be null.")
    private ParcelType parcelType;

    @Column(nullable = false)
    private Boolean isPickedUp = false;

    @ManyToOne
    @JoinColumn(name="guest_id")
    private Guest guest;


    public Parcel(  String trackingNumber, ParcelType parcelType,Guest guest) {
        this.dateEntered = LocalDateTime.now();
        this.trackingNumber = trackingNumber;
        this.parcelType = parcelType;
        this.isPickedUp=false;
        this.guest=guest;
    }


    public Parcel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime  getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(LocalDateTime  dateEntered) {
        this.dateEntered = dateEntered;
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

    public Boolean getPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(Boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcel parcel = (Parcel) o;
        return Objects.equals(getId(), parcel.getId()) &&
                Objects.equals(getTrackingNumber(), parcel.getTrackingNumber()) &&
                Objects.equals(getDateEntered(), parcel.getDateEntered()) &&
                Objects.equals(getPickedUp(),parcel.getPickedUp()) &&
                getParcelType()==parcel.getParcelType() &&
                Objects.equals(getGuest(),parcel.getGuest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrackingNumber(),getDateEntered(),getPickedUp(),getParcelType(),getGuest());
    }
}

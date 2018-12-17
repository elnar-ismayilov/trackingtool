package com.hrs.trackingtool.controller.mapper;

import com.hrs.trackingtool.dto.ParcelDTO;
import com.hrs.trackingtool.model.Guest;
import com.hrs.trackingtool.model.Parcel;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// This mapper is used to map Parcel with dto object ParcelDTO

public class ParcelMapper {

    // convert ParcelDTO to Parcel
    public static Parcel makeParcel(ParcelDTO parcelDTO,Guest guest)
    {
        return Optional.ofNullable(parcelDTO).map(c -> {
            Optional.ofNullable(guest)
                    .map(Guest::getRoomNumber).orElse(null);
            return new Parcel(parcelDTO.getTrackingNumber(),
                    parcelDTO.getParcelType(),guest);
        }).orElse(null);

    }

    // convert ParcelDTO to Parcel
    public static ParcelDTO makeParcelDTO(Parcel parcel) {
        return Optional.ofNullable(parcel).map(c -> {
            Optional.ofNullable(parcel.getGuest())
                    .map(Guest::getRoomNumber).orElse(null);
        return new ParcelDTO(parcel.getId(),
                    parcel.getTrackingNumber(),
                    GuestMapper.makeGuestDTO(parcel.getGuest()),
                    parcel.getParcelType(),
                    parcel.getDateEntered(),
                    parcel.getPickedUp());
        }).orElse(null);


    }


    // convert List<Parcel> to List<ParcelDTO>
    public static List<ParcelDTO> makeParcelDTOList(Collection<Parcel> parcels)
    {
        return parcels.stream()
                .map(ParcelMapper::makeParcelDTO)
                .collect(Collectors.toList());
    }


}

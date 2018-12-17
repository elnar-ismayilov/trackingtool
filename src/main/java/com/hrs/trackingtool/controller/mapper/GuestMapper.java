package com.hrs.trackingtool.controller.mapper;

import com.hrs.trackingtool.dto.GuestDTO;
import com.hrs.trackingtool.model.Guest;

import java.util.Optional;

// This mapper is used to map model Guest with dto object GuestDTO

public class GuestMapper {

    // convert GuestDTO to Guest
    public static Guest makeGuest(GuestDTO guestDTO )
    {
        return Optional.ofNullable(guestDTO).map(c ->
         new Guest(guestDTO.getFirstName(),guestDTO.getLastName(),
                guestDTO.getRoomNumber())
        ).orElse(null);
    }

    // convert Guest to GuestDTO
    public static GuestDTO makeGuestDTO(Guest guest){
        return Optional.ofNullable(guest).map(c ->
             new GuestDTO(c.getId(),
                    c.getCheckIN(),
                    c.getCheckOUT(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getRoomNumber())).orElse(null);

    }
}

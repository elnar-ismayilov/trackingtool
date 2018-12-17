package com.hrs.trackingtool.dao;

import com.hrs.trackingtool.model.Guest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// Access to table parcels will be covered by this repository

public interface GuestRepository extends CrudRepository<Guest, Long> {
    // get guest by First Name, Last Name and Room Number
    Optional<Guest> findByFirstNameAndLastNameAndRoomNumber(String firstName, String lastName, String roomNumber);
}

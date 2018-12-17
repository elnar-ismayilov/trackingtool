package com.hrs.trackingtool.service;


import com.hrs.trackingtool.dao.GuestRepository;
import com.hrs.trackingtool.dao.ParcelRepository;
import com.hrs.trackingtool.exception.ConstraintsViolationException;
import com.hrs.trackingtool.exception.EntityNotFoundException;
import com.hrs.trackingtool.exception.GuestCheckoutException;
import com.hrs.trackingtool.exception.ParcelAlreadyExist;
import com.hrs.trackingtool.model.Guest;
import com.hrs.trackingtool.model.Parcel;
import com.hrs.trackingtool.modelvalue.ParcelType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


//Implementation of service layer for parcels
@Service
public class ParcelServiceImpl implements ParcelService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ParcelServiceImpl.class);

    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public ParcelServiceImpl(final ParcelRepository parcelRepository, GuestRepository guestRepository) {
        this.parcelRepository = parcelRepository;
        this.guestRepository = guestRepository;
    }


    /**
     * add new parcel to guest
     *
     * @param trackingNumber
     * @param parcelType
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     * @throws GuestCheckoutException
     * @throws ParcelAlreadyExist
     */

    @Override
    public Parcel addParcel(String trackingNumber, ParcelType parcelType, String firstName, String lastName, String roomNumber) throws ConstraintsViolationException, EntityNotFoundException, GuestCheckoutException, ParcelAlreadyExist {
        Guest guest = findGuest(firstName, lastName, roomNumber);

        if (parcelRepository.findByTrackingNumber(trackingNumber).isPresent()) {
            LOG.warn("Parcel with tracking number: " + trackingNumber + " already added to guest");
            throw new ParcelAlreadyExist("Parcel with tracking number: " + trackingNumber + " already added to guest");
        }

        if (LocalDateTime.now().isAfter(guest.getCheckOUT())) {
            LOG.warn("Guest has already checked out. Parcel can not be accepted.");
            throw new GuestCheckoutException("Guest has already checked out. Parcel can not be accepted.");
        }

        Parcel parcel = new Parcel(trackingNumber, parcelType, guest);
        try {
            return parcelRepository.save(parcel);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to parcel creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }

    /**
     * delete already added parcel
     *
     * @param trackingNumber
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteParcel(String trackingNumber) throws EntityNotFoundException {
        Parcel parcel = findParcel(trackingNumber);
        parcelRepository.delete(parcel);

    }

    /**
     * change parcel status to picked up or to not picked up
     *
     * @param trackingNumber
     * @param isPickedUp
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @Override
    public void updateParcelStatus(String trackingNumber, boolean isPickedUp) throws ConstraintsViolationException, EntityNotFoundException {
        Parcel parcel = findParcel(trackingNumber);
        parcel.setPickedUp(isPickedUp);
        parcelRepository.save(parcel);
    }

    /**
     * get parcel by tracking number
     *
     * @param trackingNumber
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public Parcel getParcelByTrackingNumber(String trackingNumber) throws EntityNotFoundException {
        return findParcel(trackingNumber);
    }

    /**
     * get list of parcel by status
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @param isPickedUp
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public List<Parcel> getParcelByPickedUp(String firstName, String lastName, String roomNumber, boolean isPickedUp) throws EntityNotFoundException {
        Long guestID = findGuest(firstName, lastName, roomNumber).getId();
        return parcelRepository.findAllByGuestIdAndIsPickedUp(guestID, isPickedUp);
    }

    /**
     * check the status of guest like checked out, checked in or future guest
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public int getGuestStatus(String firstName, String lastName, String roomNumber) throws EntityNotFoundException {
        Guest guest = findGuest(firstName, lastName, roomNumber);
        if (LocalDateTime.now().isAfter(guest.getCheckOUT())) {
            return 0;
        } else if (LocalDateTime.now().isBefore(guest.getCheckIN())) {
            return 1;
        } else {
            return 2;
        }
    }


    /**
     * find parcel by tracking number
     *
     * @param trackingNumber
     * @return
     * @throws EntityNotFoundException
     */
    private Parcel findParcel(String trackingNumber) throws EntityNotFoundException {
        return parcelRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new EntityNotFoundException("Could not find parcel with tracking number: " + trackingNumber));
    }

    /**
     * find guest by Last Name, First Name and Room Number
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public Guest findGuest(String firstName, String lastName, String roomNumber) throws EntityNotFoundException {
        return guestRepository.findByFirstNameAndLastNameAndRoomNumber(firstName, lastName, roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Could not find guest with F.L. and room number: " + firstName +
                        " " + lastName +
                        " " + roomNumber));
    }


}

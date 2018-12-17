package com.hrs.trackingtool.service;

import com.hrs.trackingtool.exception.ConstraintsViolationException;
import com.hrs.trackingtool.exception.EntityNotFoundException;
import com.hrs.trackingtool.exception.GuestCheckoutException;
import com.hrs.trackingtool.exception.ParcelAlreadyExist;
import com.hrs.trackingtool.model.Guest;
import com.hrs.trackingtool.model.Parcel;
import com.hrs.trackingtool.modelvalue.ParcelType;

import java.util.List;

//Service layer of parcel
public interface ParcelService {

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
    Parcel addParcel(String trackingNumber, ParcelType parcelType, String firstName, String lastName, String roomNumber) throws ConstraintsViolationException, EntityNotFoundException, GuestCheckoutException, ParcelAlreadyExist;

    /**
     * delete already added parcel
     *
     * @param trackingNumber
     * @throws EntityNotFoundException
     */
    void deleteParcel(String trackingNumber) throws EntityNotFoundException;

    /**
     * change parcel status to picked up or to not picked up
     *
     * @param trackingNumber
     * @param isPickedUp
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    void updateParcelStatus(String trackingNumber, boolean isPickedUp) throws ConstraintsViolationException, EntityNotFoundException;

    /**
     * get parcel by tracking number
     *
     * @param trackingNumber
     * @return
     * @throws EntityNotFoundException
     */
    Parcel getParcelByTrackingNumber(String trackingNumber) throws EntityNotFoundException;

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
    List<Parcel> getParcelByPickedUp(String firstName, String lastName, String roomNumber, boolean isPickedUp) throws EntityNotFoundException;

    /**
     * check the status of guest like checked out, checked in or future guest
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    int getGuestStatus(String firstName, String lastName, String roomNumber) throws EntityNotFoundException;

    /**
     * find guest by First Name Last Name and Room Number
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    Guest findGuest(String firstName, String lastName, String roomNumber) throws EntityNotFoundException;
}

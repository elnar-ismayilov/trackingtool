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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ParcelServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ParcelRepository parcelRepository;

    @Test
    public void whenGuestDoesNotExist() {
        try {
            ParcelService parcelService = new ParcelServiceImpl(parcelRepository, guestRepository);
            when(parcelService.findGuest("test", "test", "12")).thenReturn(null);

            parcelService.addParcel("123456", ParcelType.LETTER, "test", "test", "12");
            fail();
        } catch (GuestCheckoutException e) {
            fail();
        } catch (ConstraintsViolationException e) {
            fail();
        } catch (ParcelAlreadyExist e) {
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Could not find guest with F.L. and room number: test test 12", e.getMessage());
        }
    }

    @Test
    public void whenGuestCheckedOut() {
        String checkedIN = "2017-12-08 12:30:33";
        String checkedOUT = "2017-12-22 12:30:33";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime checkedINDate = LocalDateTime.parse(checkedIN, formatter);
        LocalDateTime checkedOUTDate = LocalDateTime.parse(checkedOUT, formatter);

            ParcelService parcelService = new ParcelServiceImpl(parcelRepository, guestRepository);
            when(guestRepository.findByFirstNameAndLastNameAndRoomNumber("test", "test", "12")).
                    thenReturn(Optional.of(new Guest(checkedINDate, checkedOUTDate, "test", "test", "12")));
        try {
            parcelService.addParcel("123456", ParcelType.LETTER, "test", "test", "12");
            fail();
        } catch (ConstraintsViolationException e) {
            fail();
        } catch (EntityNotFoundException e) {
            fail();
        } catch (ParcelAlreadyExist e) {
            fail();
        } catch (GuestCheckoutException e) {
            assertEquals("Guest has already checked out. Parcel can not be accepted.", e.getMessage());
        }
    }


    @Test
    public void whenParcelAlreadyExist() {
        when(guestRepository.findByFirstNameAndLastNameAndRoomNumber("test", "test", "12")).
                thenReturn(Optional.of(new Guest( "test", "test", "12")));
        Parcel parcel=new Parcel("123456", ParcelType.LETTER, new Guest("test", "test", "12"));
        when(parcelRepository.findByTrackingNumber("123456")).
                thenReturn(Optional.of(parcel));

        ParcelService parcelService = new ParcelServiceImpl(parcelRepository, guestRepository);

        try {
            parcelService.addParcel("123456", ParcelType.LETTER, "test", "test", "12");
            fail();
        } catch (ConstraintsViolationException e) {
            fail();
        } catch (EntityNotFoundException e) {
            fail();
        } catch (GuestCheckoutException e) {
            fail();
        } catch (ParcelAlreadyExist e) {
            assertEquals("Parcel with tracking number: 123456 already added to guest", e.getMessage());
        }
    }


    @Test
    public void whenAddingParcelReturnParcel() {
        String checkedIN = "2018-12-08 12:30:33";
        String checkedOUT = "2018-12-22 12:30:33";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime checkedINDate = LocalDateTime.parse(checkedIN, formatter);
        LocalDateTime checkedOUTDate = LocalDateTime.parse(checkedOUT, formatter);
        when(guestRepository.findByFirstNameAndLastNameAndRoomNumber("test", "test", "12")).
                thenReturn(Optional.of(new Guest(checkedINDate, checkedOUTDate, "test", "test", "12")));
        Parcel parcel = new Parcel("123456", ParcelType.LETTER, new Guest("test", "test", "12"));

        when(parcelRepository.save(any(Parcel.class))).
                thenReturn(parcel);
        ParcelService parcelService = new ParcelServiceImpl(parcelRepository, guestRepository);

        try {
            Parcel newParcel = parcelService.addParcel("123456", ParcelType.LETTER, "test", "test", "12");
            assertEquals("123456", newParcel.getTrackingNumber());
            assertEquals(ParcelType.LETTER, newParcel.getParcelType());
        } catch (Exception e) {
            fail();
        }
    }


}

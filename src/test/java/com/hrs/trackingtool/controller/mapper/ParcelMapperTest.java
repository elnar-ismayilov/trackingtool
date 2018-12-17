package com.hrs.trackingtool.controller.mapper;

import com.hrs.trackingtool.dto.GuestDTO;
import com.hrs.trackingtool.dto.ParcelDTO;
import com.hrs.trackingtool.model.Guest;
import com.hrs.trackingtool.model.Parcel;
import com.hrs.trackingtool.modelvalue.ParcelType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParcelMapperTest {

    @Test
    public void whenCreatingParcelDTOFromParcel() {
        Parcel parcel = new Parcel("123456", ParcelType.LETTER,new Guest("test","test","12"));
        ParcelDTO parcelDTO = ParcelMapper.makeParcelDTO(parcel);

        assertEquals(null, parcelDTO.getId());
        assertEquals("123456", parcelDTO.getTrackingNumber());
        assertEquals(ParcelType.LETTER, parcelDTO.getParcelType());
        assertEquals(false, parcelDTO.getPickedUp());
        assertEquals("test", parcelDTO.getGuestDTO().getFirstName());
        assertEquals("test", parcelDTO.getGuestDTO().getLastName());
        assertEquals("12", parcelDTO.getGuestDTO().getRoomNumber());

    }

    @Test
    public void whenCreatingParcelDTOFromParcelReturnNullIfParcelIsNull() {
        ParcelDTO parcelDTO = ParcelMapper.makeParcelDTO(null);
        assertEquals(null, parcelDTO);
    }

    @Test
    public void whenCreatingParcelFromParcelDTO() {
        ParcelDTO parcelDTO = new ParcelDTO(null,"123456",new GuestDTO("test","test","12"),
                ParcelType.LETTER,LocalDateTime.now() ,false);
        Parcel parcel = ParcelMapper.makeParcel(parcelDTO,GuestMapper.makeGuest(parcelDTO.getGuestDTO()));

        assertEquals(null, parcel.getId());
        assertEquals("123456", parcel.getTrackingNumber());
        assertEquals(ParcelType.LETTER, parcel.getParcelType());
        assertEquals(false, parcel.getPickedUp());
        assertEquals("test", parcel.getGuest().getFirstName());
        assertEquals("test", parcel.getGuest().getLastName());
        assertEquals("12", parcel.getGuest().getRoomNumber());

    }

    @Test
    public void whenCreatingParcelFromParcelDTOReturnNullIfParcelIsNull() {
        Parcel parcel = ParcelMapper.makeParcel(null,null);
        assertEquals(null, parcel);
    }
}

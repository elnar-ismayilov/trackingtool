package com.hrs.trackingtool.controller.mapper;

import com.hrs.trackingtool.dto.GuestDTO;
import com.hrs.trackingtool.model.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class GuestMapperTest {

    @Test
    public void whenCreatingGuestDTOFromGuest() {
        Guest guest = new Guest("test","test","12");
        GuestDTO guestDTO = GuestMapper.makeGuestDTO(guest);

        assertEquals("test", guestDTO.getFirstName());
        assertEquals("test", guestDTO.getLastName());
        assertEquals("12", guestDTO.getRoomNumber());

    }

    @Test
    public void whenCreatingGuestDTOFromGuestReturnNullIfGuestIsNull() {
        GuestDTO guestDTO = GuestMapper.makeGuestDTO(null);
        assertEquals(null, guestDTO);
    }

    @Test
    public void whenCreatingGuestFromGuestDTO() {
        GuestDTO guestDTO = new GuestDTO("test","test","12");
        Guest guest = GuestMapper.makeGuest(guestDTO);

        assertEquals("test", guest.getFirstName());
        assertEquals("test", guest.getLastName());
        assertEquals("12", guest.getRoomNumber());

    }

    @Test
    public void whenCreatingGuestFromGuestDTOReturnNullIfGuestIsNull() {
        Guest guest = GuestMapper.makeGuest(null);
        assertEquals(null, guest);
    }
}

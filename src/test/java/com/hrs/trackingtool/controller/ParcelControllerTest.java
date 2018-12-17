package com.hrs.trackingtool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.trackingtool.dto.GuestDTO;
import com.hrs.trackingtool.dto.ParcelDTO;
import com.hrs.trackingtool.exception.EntityNotFoundException;
import com.hrs.trackingtool.exception.GuestCheckoutException;
import com.hrs.trackingtool.exception.ParcelAlreadyExist;
import com.hrs.trackingtool.model.Guest;
import com.hrs.trackingtool.model.Parcel;
import com.hrs.trackingtool.modelvalue.ParcelType;
import com.hrs.trackingtool.service.ParcelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ParcelController.class)
public class ParcelControllerTest {
    private MediaType contentType = new MediaType(APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private ParcelService parcelService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ParcelController(parcelService)).build();
    }


    //Testing adding parcels

    @Test
    public void whenAddingParcelReturnNewObject() throws Exception {
        ParcelDTO parcelDTO = new ParcelDTO(1l, "123456", new GuestDTO("test","test","12"),
                         ParcelType.LETTER,null,false);

        Guest guest = new Guest("test", "test", "12");


        Parcel parcel = new Parcel("123456",ParcelType.LETTER,guest);
        when(parcelService.addParcel("123456",ParcelType.LETTER,"test","test","12")).thenReturn(parcel);

        MvcResult result = this.mockMvc.perform(post("/hrs/parcels").accept(contentType).contentType(contentType).content(asJsonString(parcelDTO)))
                .andExpect(status().isCreated()).andReturn();

        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("123456",parcelMap.get("trackingNumber"));
        assertEquals("LETTER",parcelMap.get("parcelType"));
        assertEquals(false,parcelMap.get("pickedUp"));
    }

    @Test
    public void whenAddingParcelReturnAnErrorIfGuestIsNotExist() throws Exception {
        ParcelDTO parcelDTO = new ParcelDTO(1l, "123456", new GuestDTO("test","test","12"),
                ParcelType.LETTER,null,false);

        when(parcelService.addParcel("123456",ParcelType.LETTER,"test","test","12")).
        thenThrow(new EntityNotFoundException("Could not find entity"));

        MvcResult result =this.mockMvc.perform(post("/hrs/parcels").accept(contentType).contentType(contentType).content(asJsonString(parcelDTO)))
                .andExpect(status().isBadRequest()).andReturn();
        String content = result.getResponse().getErrorMessage();
        assertEquals("Could not find entity",content);
    }

    @Test
    public void whenAddingParcelReturnAnErrorIfGuestCheckedOut() throws Exception {
        ParcelDTO parcelDTO = new ParcelDTO(1l, "123456", new GuestDTO("test","test","12"),
                ParcelType.LETTER,null,false);

        when(parcelService.addParcel("123456",ParcelType.LETTER,"test","test","12")).
                thenThrow(new GuestCheckoutException("Guest has already checked out. Parcel can not be accepted."));

        MvcResult result =this.mockMvc.perform(post("/hrs/parcels").accept(contentType).contentType(contentType).content(asJsonString(parcelDTO)))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getErrorMessage();
        assertEquals("Guest has already checked out. Parcel can not be accepted.",content);
    }

    @Test
    public void whenAddingParcelReturnAnErrorParcelAlreadyExist() throws Exception {
        ParcelDTO parcelDTO = new ParcelDTO(1l, "123456", new GuestDTO("test","test","12"),
                ParcelType.LETTER,null,false);

        when(parcelService.addParcel("123456",ParcelType.LETTER,"test","test","12")).
                thenThrow(new ParcelAlreadyExist("Parcel already exist."));

        MvcResult result =this.mockMvc.perform(post("/hrs/parcels").accept(contentType).contentType(contentType).content(asJsonString(parcelDTO)))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getErrorMessage();
        assertEquals("Parcel already exist.",content);
    }

   //Testing deleting parcels
    @Test
    public void whenDeletingAParcelReturnOKIfSuccessful() throws Exception {
        MvcResult result =this.mockMvc.perform(delete("/hrs/parcels/123456").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.105",parcelMap.get("code"));
        assertEquals("Parcel was deleted successfully.",parcelMap.get("message"));
    }


    //Testing updating parcels
    @Test
    public void whenUpdatingAParcelReturnGuestReturnedParcel() throws Exception {
        MvcResult result =this.mockMvc.perform(put("/hrs/parcels/123456").accept(contentType).contentType(contentType).param("isPickedUp","false"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.107",parcelMap.get("code"));
        assertEquals("Parcel was returned by guest.",parcelMap.get("message"));
    }



    @Test
    public void whenUpdatingAParcelReturnParcelPickedUpByGuest() throws Exception {
        MvcResult result =this.mockMvc.perform(put("/hrs/parcels/123456").accept(contentType).contentType(contentType).param("isPickedUp","true"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.106",parcelMap.get("code"));
        assertEquals("Parcel was picked up by guest.",parcelMap.get("message"));
    }

    //get Parcel testing
    @Test
    public void whenFindingAParcelReturnAnErrorIfParcelIsNotFound() throws Exception {
        when(parcelService.getParcelByTrackingNumber("123456")).thenThrow(new EntityNotFoundException("Could not find entity"));

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/123456").accept(contentType).contentType(contentType))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getErrorMessage();
        assertEquals("Could not find entity",content);;
    }


    @Test
    public void whenFindingAParcelReturnAParcelIfParcelIsFound() throws Exception {
        Guest guest = new Guest("test", "test", "12");

        Parcel parcel = new Parcel("123456",ParcelType.LETTER,guest);
        when(parcelService.getParcelByTrackingNumber("123456")).thenReturn(parcel);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/123456").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("123456",parcelMap.get("trackingNumber"));
        assertEquals("LETTER",parcelMap.get("parcelType"));
        assertEquals(false,parcelMap.get("pickedUp"));
    }


    //get Parcel List testing (picked or not picked up)

    @Test
    public void whenFindingAPickedUpParcelsReturnAListOfParcel() throws Exception {
        Guest guest = new Guest("test", "test", "12");

        List<Parcel> parcels =new ArrayList<>();
        Parcel parcel1 = new Parcel("123456",ParcelType.LETTER,guest);
        Parcel parcel2 = new Parcel("123456",ParcelType.LETTER,guest);
        parcel1.setPickedUp(true);
        parcel2.setPickedUp(true);
        parcels.add(parcel1);
        parcels.add(parcel2);
        when(parcelService.getParcelByPickedUp("test","test","12",true)).thenReturn(parcels);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/pickedUp/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        List<HashMap<String,String>> parcelList=asObjectList(content);
        assertEquals("123456",parcelList.get(0).get("trackingNumber"));
        assertEquals("LETTER",parcelList.get(0).get("parcelType"));
        assertEquals(true,parcelList.get(0).get("pickedUp"));
        assertEquals("123456",parcelList.get(1).get("trackingNumber"));
        assertEquals("LETTER",parcelList.get(1).get("parcelType"));
        assertEquals(true,parcelList.get(01).get("pickedUp"));
    }


    @Test
    public void whenFindingNotPickedUpParcelsReturnAListOfParcel() throws Exception {
        Guest guest = new Guest("test", "test", "12");

        List<Parcel> parcels =new ArrayList<>();
        Parcel parcel1 = new Parcel("123456",ParcelType.LETTER,guest);
        Parcel parcel2 = new Parcel("123456",ParcelType.LETTER,guest);
        parcels.add(parcel1);
        parcels.add(parcel2);
        when(parcelService.getParcelByPickedUp("test","test","12",false)).thenReturn(parcels);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        List<HashMap<String,String>> parcelList=asObjectList(content);
        assertEquals("123456",parcelList.get(0).get("trackingNumber"));
        assertEquals("LETTER",parcelList.get(0).get("parcelType"));
        assertEquals(false,parcelList.get(0).get("pickedUp"));
        assertEquals("123456",parcelList.get(1).get("trackingNumber"));
        assertEquals("LETTER",parcelList.get(1).get("parcelType"));
        assertEquals(false,parcelList.get(01).get("pickedUp"));
    }


    //get guest status test)

    @Test
    public void whenGettingGuestStatusReturnGuestCheckout() throws Exception {
        when(parcelService.getGuestStatus("test","test","12")).thenReturn(0);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/GuestCheckedOut/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.101",parcelMap.get("code"));
        assertEquals("Guest has already checked out.",parcelMap.get("message"));
    }

    @Test
    public void whenGettingGuestStatusReturnGuestWillCheckIn() throws Exception {
        when(parcelService.getGuestStatus("test","test","12")).thenReturn(1);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/GuestCheckedOut/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.102",parcelMap.get("code"));
        assertEquals("Guest has not checked in yet.",parcelMap.get("message"));
    }


    @Test
    public void whenGettingGuestStatusReturnGuestCheckedIn() throws Exception {
        when(parcelService.getGuestStatus("test","test","12")).thenReturn(2);

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/GuestCheckedOut/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        HashMap<String,String> parcelMap=asObject(content);
        assertEquals("200.103",parcelMap.get("code"));
        assertEquals("Guest has already checked in.",parcelMap.get("message"));
    }

    @Test
    public void whenGettingGuestStatusReturnAnErrorGuestNotFound() throws Exception {
        when(parcelService.getGuestStatus("test","test","12")).thenThrow(new EntityNotFoundException("Could not find entity"));

        MvcResult result =this.mockMvc.perform(get("/hrs/parcels/GuestCheckedOut/firstName/test/lastName/test/roomNumber/12").accept(contentType).contentType(contentType))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getErrorMessage();
        assertEquals("Could not find entity",content);
    }

    public static String asJsonString(final ParcelDTO parcelDTO) {
        try {
            return new ObjectMapper().writeValueAsString(parcelDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap asObject(String parcel) {
        try {
            return new ObjectMapper().readValue(parcel, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList asObjectList(String parcel) {
        try {
            return new ObjectMapper().readValue(parcel, ArrayList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

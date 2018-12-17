package com.hrs.trackingtool.controller;

import com.hrs.trackingtool.controller.mapper.ParcelMapper;
import com.hrs.trackingtool.dto.ParcelDTO;
import com.hrs.trackingtool.exception.ConstraintsViolationException;
import com.hrs.trackingtool.exception.EntityNotFoundException;
import com.hrs.trackingtool.exception.GuestCheckoutException;
import com.hrs.trackingtool.exception.ParcelAlreadyExist;
import com.hrs.trackingtool.model.CustomResponse;
import com.hrs.trackingtool.model.Parcel;
import com.hrs.trackingtool.modelvalue.CustomHttpStatus;
import com.hrs.trackingtool.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


 // All operations with a Parcel will be routed by this controller.


@RestController
@RequestMapping("hrs/parcels")
public class ParcelController {


    private ParcelService parcelService;

    @Autowired
    public ParcelController(final ParcelService parcelService)
    {
        this.parcelService = parcelService;
    }


    /** add new parcel
     *
     * @param parcelDTO
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     * @throws GuestCheckoutException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelDTO addParcel(@Valid @RequestBody ParcelDTO parcelDTO) throws ConstraintsViolationException,EntityNotFoundException,GuestCheckoutException,ParcelAlreadyExist {
        Parcel parcel = parcelService.addParcel(parcelDTO.getTrackingNumber(),parcelDTO.getParcelType(),parcelDTO.getGuestDTO().getFirstName(),
                parcelDTO.getGuestDTO().getLastName(),parcelDTO.getGuestDTO().getRoomNumber());
        return ParcelMapper.makeParcelDTO(parcel);
    }

    /**delete already added parcel
     *
     * @param trackingNumber
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{trackingNumber}")
    public CustomResponse deleteParcel(@Valid @PathVariable String trackingNumber) throws EntityNotFoundException
    {
        parcelService.deleteParcel(trackingNumber);
        return new CustomResponse(CustomHttpStatus.PARCEL_DELETED);
    }

    /**change parcel status to picked up or to not picked up
     *
     * @param trackingNumber
     * @param isPickedUp
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @PutMapping("{trackingNumber}")
    public CustomResponse updateParcelStatus(
            @Valid @PathVariable String trackingNumber,
            @RequestParam boolean isPickedUp)
            throws ConstraintsViolationException, EntityNotFoundException
    {
        parcelService.updateParcelStatus(trackingNumber,isPickedUp);
        return isPickedUp?(new CustomResponse(CustomHttpStatus.PARCEL_PICKEDUP)):
                (new CustomResponse(CustomHttpStatus.PARCEL_RETURNED));
    }


    /** get parcel by tracking number
     *
     * @param trackingNumber
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{trackingNumber}")
    public ParcelDTO getParcel(@Valid @PathVariable String trackingNumber) throws EntityNotFoundException
    {
        return ParcelMapper.makeParcelDTO(parcelService.getParcelByTrackingNumber(trackingNumber));
    }

    /**get list of parcel which is not picked up
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/firstName/{firstName}/lastName/{lastName}/roomNumber/{roomNumber}")
    public List<ParcelDTO> getParcelNotPickedUp(@Valid @PathVariable String firstName,
                                                @Valid @PathVariable String lastName,
                                                @Valid @PathVariable String roomNumber) throws EntityNotFoundException
    {
        return ParcelMapper.makeParcelDTOList(parcelService.getParcelByPickedUp(firstName,lastName,roomNumber,false));
    }

    /** get list of parcel which is  picked up
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("pickedUp/firstName/{firstName}/lastName/{lastName}/roomNumber/{roomNumber}")
    public List<ParcelDTO> getParcelPickedUp(@Valid @PathVariable String firstName,
                                                @Valid @PathVariable String lastName,
                                                @Valid @PathVariable String roomNumber) throws EntityNotFoundException
    {
        return ParcelMapper.makeParcelDTOList(parcelService.getParcelByPickedUp(firstName,lastName,roomNumber,true));
    }

    /** check the status of guest like checked out, checked in or future guest
     *
     * @param firstName
     * @param lastName
     * @param roomNumber
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("GuestCheckedOut/firstName/{firstName}/lastName/{lastName}/roomNumber/{roomNumber}")
    public CustomResponse isGuestCheckedOut(@Valid @PathVariable String firstName,
                                            @Valid @PathVariable String lastName,
                                            @Valid @PathVariable String roomNumber) throws EntityNotFoundException {
        switch (parcelService.getGuestStatus(firstName, lastName, roomNumber)) {
            case 0:
                return new CustomResponse(CustomHttpStatus.GUEST_CHECKEDOUT);
            case 1:
                return new CustomResponse(CustomHttpStatus.GUEST_CHECKED);
            case 2:
                return new CustomResponse(CustomHttpStatus.GUEST_CHECKEDIN);
            default:
                return new CustomResponse(CustomHttpStatus.GUEST_UNKNOWN);
        }
    }

}

package com.hrs.trackingtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// Custom exception if parcell already exist
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Parcel already exist.")
public class ParcelAlreadyExist extends Exception {


    private static final long serialVersionUID = -760969946371442607L;

    public ParcelAlreadyExist(String message)
        {
            super(message);
        }


}

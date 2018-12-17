package com.hrs.trackingtool.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception if guest already checked out however receptionist  try to accept parcel
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Guest has already checked out. Parcel can not be accepted.")
public class GuestCheckoutException extends Exception {


    private static final long serialVersionUID = -5152357038840878061L;

    public GuestCheckoutException(String message)
    {
        super(message);
    }
}

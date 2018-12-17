package com.hrs.trackingtool.exception;

// Custom exception if entity could not be found
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find entity")
public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 6914255137202263624L;

    public EntityNotFoundException(String message)
    {
        super(message);
    }
}

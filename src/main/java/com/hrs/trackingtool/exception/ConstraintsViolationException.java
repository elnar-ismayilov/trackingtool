package com.hrs.trackingtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// Custom exception if constraints will be violated
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated ...")
public class ConstraintsViolationException extends Exception{


    private static final long serialVersionUID = -6114014832178586490L;

    public ConstraintsViolationException(String message)
    {
        super(message);
    }

}

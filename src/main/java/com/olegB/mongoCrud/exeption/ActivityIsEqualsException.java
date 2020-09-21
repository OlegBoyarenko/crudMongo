package com.olegB.mongoCrud.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActivityIsEqualsException extends Exception{

    private static final long serialVersionUID = 1L;

    public ActivityIsEqualsException(String message){
        super(message);
    }
}

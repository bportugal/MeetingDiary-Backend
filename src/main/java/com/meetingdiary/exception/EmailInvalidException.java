package com.meetingdiary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email has an invalid format.")
public class EmailInvalidException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;

    public EmailInvalidException(String message) {
        super(message);
    }
}

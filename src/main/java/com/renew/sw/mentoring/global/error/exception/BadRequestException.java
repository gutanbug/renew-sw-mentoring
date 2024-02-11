package com.renew.sw.mentoring.global.error.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends LocalizedMessageException {
    public BadRequestException(Throwable t) {
        super(t, HttpStatus.BAD_REQUEST, "invalid.request");
    }
}

package com.mjc.school.service.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionPayload {
    private final String msg;
    private final HttpStatus httpStatus;
    private final LocalDateTime dateTime;

    public ExceptionPayload(String msg, HttpStatus httpStatus, LocalDateTime dateTime) {
        this.msg = msg;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

    public String getMsg() {
        return msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

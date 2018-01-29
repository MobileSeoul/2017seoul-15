package com.stm.common.dto;

/**
 * Created by ㅇㅇ on 2017-03-15.
 */

public class HttpErrorDto {
    private int statusCode;
    private String message;

    public HttpErrorDto() {
    }

    public HttpErrorDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}

package com.fpdual.javaweb.enums;

import lombok.Getter;

public enum HttpStatus {
    OK(200),
    NO_CONTENT(204),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500);

    @Getter
    private final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;

    }
}
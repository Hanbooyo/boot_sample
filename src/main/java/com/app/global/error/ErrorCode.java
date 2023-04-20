package com.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "businessExceptionTest")
    ;

    ErrorCode(HttpStatus httpStatus, String errorcode, String message) {
        this.httpStatus = httpStatus;
        this.errorcode = errorcode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorcode;
    private String message;


}

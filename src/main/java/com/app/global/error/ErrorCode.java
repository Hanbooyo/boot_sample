package com.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "businessExceptionTest"),

    // 인증
    TOKEN_EXPRIED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "올바른 토큰이 아닙니다.")

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

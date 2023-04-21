package com.app.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new Default(); // 이미 구현된것 가져다 쓸 예정
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("{} 요청 실패, status : {}, reason : {}", methodKey, response.status(), response.reason());
        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status()); //반환된 HttpStatus를 객체로 만듦
        if(httpStatus.is5xxServerError()) { //500번대 에러가 오면
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request()
            );
        }
        return errorDecoder.decode(methodKey, response);
    }
}

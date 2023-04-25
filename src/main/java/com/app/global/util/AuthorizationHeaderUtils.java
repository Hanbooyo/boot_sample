package com.app.global.util;

import com.app.domain.member.constant.MemberType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.jwt.constant.GrantType;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        // 1. authorizaionHeader 필수 체크
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorizaionHeader Baerer 체크
        String[] authorizaions = authorizationHeader.split(" ");
        if(authorizaions.length <2 || (!GrantType.BEARER.getType().equals(authorizaions[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER);
        }
    }

}

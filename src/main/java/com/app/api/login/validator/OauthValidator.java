package com.app.api.login.validator;

import com.app.domain.member.constant.MemberType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.jwt.constant.GrantType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OauthValidator {

    public void validateAuthorization(String authorizationHeader) {

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

    public void validateMemberType(String memberType) {
        if(!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }
}

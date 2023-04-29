package com.app.api.logout.service;

import com.app.domain.member.service.MemberService;
import com.app.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final MemberService memberService;

    private final TokenManager tokenManager;
    public void logout(String accessToken) {

        // 1. 토큰 검증
        tokenManager.validateToken(accessToken);

        // 2. 토큰 타입 검증
        tokenManager.getTokenClaims(accessToken);

        //
    }
}

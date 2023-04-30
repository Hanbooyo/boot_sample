package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId, Role role){
        //accessToken에 멤버 아이디와 role을 담아서 보낼예정
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(memberId,role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);
        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    } //만료시간 반환 (현재시간기준 15분)

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    } //만료시간 반환 (현재시간기준 2주)

    public String createAccessToken(Long memberId, Role role, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰의 제목
                .setIssuedAt(new Date())                // 토큰이 발급된 시간
                .setExpiration(expirationTime)          // 토큰이 만료되는 시간
                .claim("memberId", memberId)      // 회원 아이디
                .claim("role", role)              // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8)) // 서명알고리즘, 인코딩타입 설정
                .setHeaderParam("typ", "JWT")
                .compact();
        return accessToken;
    }
    public String createRefreshToken(Long memberId, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())    // 토큰의 제목
                .setIssuedAt(new Date())                // 토큰이 발급된 시간
                .setExpiration(expirationTime)          // 토큰이 만료되는 시간
                .claim("memberId", memberId)      // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8)) // 서명알고리즘, 인코딩타입 설정
                .setHeaderParam("typ", "JWT")
                .compact();
        return refreshToken;
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
        }catch (ExpiredJwtException e) {
            log.info("token 만료 ",e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPRIED);
        }catch (Exception e) {
            //우리가 발급한 토큰외 혹은 다른 오류
            log.info("유효하지 않은 토큰", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);

        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        }catch (Exception e) {
            log.info("유효하지 않은 토큰", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

}

package com.app.global.jwt.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
//모든필드생성자 생성     기본생성자 생성
public class JwtTokenDto {

    private String grantType;
    private String accessToken;
    private Date accessTokenExpireTime;
    private String refreshToken;
    private Date refreshTokenExpireTime;


}

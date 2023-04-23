package com.app.external.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KakaoUserInfoResponseDto {

    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
    // camelCase로 받을수있게처리

    @Getter @Setter
    public static class KakaoAccount {
        private String email;
        private Profile profile;
        @Getter @Setter //자동 import때문에 재정의
        public static class Profile {

            private String nickname;

            @JsonProperty("thumbnail_image_url") // _ 를 카멜케이스로 받을수있게
            private String thumbnailImageUrl;

        }
    }
}

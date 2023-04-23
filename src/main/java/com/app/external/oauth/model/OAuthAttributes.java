package com.app.external.oauth.model;

import com.app.domain.member.constant.MemberType;
import com.app.domain.member.constant.Role;
import com.app.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder
public class OAuthAttributes {
    // Social platform에서 각각 다른형태로 반환되는 정보들을 통일하기 위한 클래스

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {
        // 이 OAuthAttributes에 있는 정보를 Member에 담아서 객체를 만들고 그 객체를 이용해 회원가입 예정
        return Member.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }
}

package waktfolio.application.mapper.member;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.Member;
import waktfolio.rest.dto.member.LoginMemberResponse;
import waktfolio.rest.dto.member.RegisterMemberRequest;

@Component
public class MemberMapper {
    public LoginMemberResponse loginMemberResponseOf(String name, String accessToken){
        return LoginMemberResponse.builder()
                .name(name)
                .accessToken(accessToken)
                .build();
    }
    public Member memberFrom(RegisterMemberRequest registerMemberRequest, String encodePassword){
        return Member.builder()
                .loginId(registerMemberRequest.getLoginId())
                .password(encodePassword)
                .profileImagePath(registerMemberRequest.getProfileImagePath())
                .build();
    }
}

package waktfolio.application.mapper.member;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.member.Member;
import waktfolio.domain.entity.member.MemberPermission;
import waktfolio.rest.dto.member.LoginMemberResponse;
import waktfolio.rest.dto.member.MemberProfileResponse;
import waktfolio.rest.dto.member.RegisterMemberRequest;
import waktfolio.rest.dto.member.UpdateMemberRequest;

import java.util.Optional;

@Component
public class MemberMapper {
    public LoginMemberResponse loginMemberResponseOf(String name, String accessToken,String profileImagePath){
        return LoginMemberResponse.builder()
                .name(name)
                .accessToken(accessToken)
                .profileImagePath(profileImagePath)
                .build();
    }
    public Member memberFrom(RegisterMemberRequest registerMemberRequest, String encodePassword){
        return Member.builder()
                .loginId(registerMemberRequest.getLoginId())
                .password(encodePassword)
                .name(registerMemberRequest.getName())
                .memberPermission(MemberPermission.MEMBER)
                .build();
    }
    public MemberProfileResponse memberProfileResponseOf(Member member , Long totalLike , Long totalView){
        return MemberProfileResponse.builder()
                .name(member.getName())
                .profileImagePath(member.getProfileImagePath())
                .totalLike(totalLike)
                .totalView(totalView)
                .build();
    }
    public void memberUpdateFrom(Member member, UpdateMemberRequest updateMemberRequest){
        Optional.ofNullable(updateMemberRequest.getName()).ifPresent(member::setName);
    }
}

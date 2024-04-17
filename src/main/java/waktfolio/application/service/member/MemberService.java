package waktfolio.application.service.member;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.member.LoginMemberRequest;
import waktfolio.rest.dto.member.LoginMemberResponse;
import waktfolio.rest.dto.member.MemberProfileResponse;
import waktfolio.rest.dto.member.RegisterMemberRequest;

public interface MemberService {

    LoginMemberResponse login(LoginMemberRequest loginMemberRequest);
    void register(RegisterMemberRequest registerMemberRequest);
    MemberProfileResponse profile(HttpServletRequest request);
}

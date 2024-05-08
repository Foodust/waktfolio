package waktfolio.application.service.member;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.member.*;

public interface MemberService {

    LoginMemberResponse loginMember(LoginMemberRequest loginMemberRequest);
    void updateMember(HttpServletRequest request, UpdateMemberRequest updateMemberRequest);
    void deleteMember(HttpServletRequest request);
    void registerMember(RegisterMemberRequest registerMemberRequest);
    MemberProfileResponse profileMember(HttpServletRequest request);
}

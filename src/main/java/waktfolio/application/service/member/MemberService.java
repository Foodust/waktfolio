package waktfolio.application.service.member;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.member.*;

public interface MemberService {

    LoginMemberResponse login(LoginMemberRequest loginMemberRequest);
    void update(HttpServletRequest request,UpdateMemberRequest updateMemberRequest);
    void register(RegisterMemberRequest registerMemberRequest);
    MemberProfileResponse profile(HttpServletRequest request);
}

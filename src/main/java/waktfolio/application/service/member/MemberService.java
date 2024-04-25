package waktfolio.application.service.member;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.member.*;

import java.io.IOException;

public interface MemberService {

    LoginMemberResponse login(LoginMemberRequest loginMemberRequest);
    void update(HttpServletRequest request,UpdateMemberRequest updateMemberRequest);
    void delete(HttpServletRequest request);
    void register(RegisterMemberRequest registerMemberRequest);
    MemberProfileResponse profile(HttpServletRequest request);
}

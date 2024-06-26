package waktfolio.rest.controller.member;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.member.MemberService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.member.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/login")
    @Tag(name = "로그인")
    public ResponseEntity<ApiResponse> loginMember(HttpServletRequest request, @RequestBody LoginMemberRequest loginMemberRequest) {
        LoginMemberResponse login = memberService.loginMember(loginMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request,login),HttpStatus.OK);
    }
    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Tag(name = "회원가입")
    public ResponseEntity<ApiResponse> registerMember(HttpServletRequest request, @ModelAttribute RegisterMemberRequest registerMemberRequest){
        memberService.registerMember(registerMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Tag(name = "계정 정보 업데이트")
    public ResponseEntity<ApiResponse> updateMember(HttpServletRequest request, @ModelAttribute UpdateMemberRequest updateMemberRequest){
        memberService.updateMember(request, updateMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @GetMapping("")
    @Tag(name = "내 정보 조회")
    public ResponseEntity<ApiResponse> getProfile(HttpServletRequest request){
        MemberProfileResponse profile = memberService.profileMember(request);
        return new ResponseEntity<>(ApiResponse.of(request, profile),HttpStatus.OK);
    }
    @DeleteMapping("")
    @Tag(name = "탈퇴")
    public ResponseEntity<ApiResponse> deleteMember(HttpServletRequest request){
        memberService.deleteMember(request);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
}

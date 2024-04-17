package waktfolio.rest.controller.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse> loginMember(HttpServletRequest request, @RequestBody LoginMemberRequest loginMemberRequest){
        LoginMemberResponse login = memberService.login(loginMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request,login),HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse> registerMember(HttpServletRequest request, @RequestBody RegisterMemberRequest registerMemberRequest){
        memberService.register(registerMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @PatchMapping("")
    public ResponseEntity<ApiResponse> updateMember(HttpServletRequest request, @RequestBody UpdateMemberRequest updateMemberRequest){
        memberService.update(request, updateMemberRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse> getProfile(HttpServletRequest request){
        MemberProfileResponse profile = memberService.profile(request);
        return new ResponseEntity<>(ApiResponse.of(request, profile),HttpStatus.OK);
    }
}

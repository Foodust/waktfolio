package waktfolio.rest.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.admin.AdminService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.content.FindContentResponse;
import waktfolio.rest.dto.member.LoginMemberRequest;
import waktfolio.rest.dto.member.LoginMemberResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("")
    @Tag(name = "사용 안하는 콘텐츠 리스트")
    public ResponseEntity<ApiResponse> getUseFalseList(HttpServletRequest request, @RequestBody LoginMemberRequest loginMemberRequest, Pageable pageable) {
        List<FindContentResponse> beforeList = adminService.getBeforeList(pageable);
        return new ResponseEntity<>(ApiResponse.of(request,beforeList), HttpStatus.OK);
    }

}

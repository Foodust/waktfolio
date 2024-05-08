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
import waktfolio.rest.dto.admin.ContentListRequest;
import waktfolio.rest.dto.content.FindContentDetailResponse;
import waktfolio.rest.dto.content.FindContentResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("")
    @Tag(name = "검수 요청된 콘텐츠")
    public ResponseEntity<ApiResponse> getUseFalseList(HttpServletRequest request, Pageable pageable) {
        List<FindContentDetailResponse> beforeList = adminService.getBeforeList(pageable);
        return new ResponseEntity<>(ApiResponse.of(request, beforeList), HttpStatus.OK);
    }

    @PatchMapping("")
    @Tag(name = "검수 반영하기")
    public ResponseEntity<ApiResponse> updateContents(HttpServletRequest request, @RequestBody ContentListRequest contentListRequest) {
        adminService.updateContents(contentListRequest);
        return new ResponseEntity<>(ApiResponse.of(request), HttpStatus.OK);
    }

    @DeleteMapping("")
    @Tag(name = "검수 삭제하기")
    public ResponseEntity<ApiResponse> deleteContents(HttpServletRequest request, @RequestBody ContentListRequest contentListRequest) {
        adminService.deleteContents(contentListRequest);
        return new ResponseEntity<>(ApiResponse.of(request), HttpStatus.OK);
    }
}

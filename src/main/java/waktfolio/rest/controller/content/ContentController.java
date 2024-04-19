package waktfolio.rest.controller.content;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.content.ContentService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.BaseListDto;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("/main")
    @Tag(name = "메인 콘텐츠 가져오기")
    public ResponseEntity<ApiResponse> getMainContent(HttpServletRequest request) {
        MainContentResponse main = contentService.getMainContent();
        return new ResponseEntity<>(ApiResponse.of(request, main), HttpStatus.OK);
    }

    @PatchMapping("/{contentId}/like")
    @Tag(name = "콘텐츠 좋아요 누르기")
    public ResponseEntity<ApiResponse> like(HttpServletRequest request, @PathVariable UUID contentId) {
        LikeResponse isLike = contentService.upLikeContent(request, contentId);
        return new ResponseEntity<>(ApiResponse.of(request,isLike), HttpStatus.OK);
    }
    @GetMapping("")
    @Tag(name = "콘텐츠 검색하기")
    public ResponseEntity<ApiResponse> getAllContentGroup(HttpServletRequest request,@RequestParam List<String> tags){
        List<FindContentGroupResponse> allContentGroup = contentService.getAllContentGroup(tags);
        return new ResponseEntity<>(ApiResponse.of(request,allContentGroup), HttpStatus.OK);
    }

    @GetMapping("/{contentGroupId}")
    @Tag(name = "콘텐츠 상세조회 하기")
    public ResponseEntity<ApiResponse> getContent(HttpServletRequest request, @PathVariable UUID contentGroupId, List<String> tags) {
        List<FindContentDetail> allContent = contentService.getAllContent(contentGroupId, tags);
        return new ResponseEntity<>(ApiResponse.of(request,allContent), HttpStatus.OK);
    }
}

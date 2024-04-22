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
    @Tag(name = "그룹 검색하기")
    public ResponseEntity<ApiResponse> getAllContentGroup(HttpServletRequest request, @RequestParam List<String> tags, Pageable pageable){
        List<FindMemberResponse> allContentGroup = contentService.findAllContentGroup(tags,pageable);
        return new ResponseEntity<>(ApiResponse.of(request,allContentGroup), HttpStatus.OK);
    }

    @GetMapping("/{contentGroupId}")
    @Tag(name = "그룹 상세조회 하기")
    public ResponseEntity<ApiResponse> getContentGroup(HttpServletRequest request, @PathVariable UUID contentGroupId, List<String> tags) {
        List<FindContentResponse> allContentGroup = contentService.getContentGroup(contentGroupId, tags);
        return new ResponseEntity<>(ApiResponse.of(request,allContentGroup), HttpStatus.OK);
    }
    @GetMapping("/{contentGroupId}/{contentId}")
    @Tag(name = "콘텐트 상세조회 하기")
    public ResponseEntity<ApiResponse> getContent(HttpServletRequest request, @PathVariable UUID contentGroupId, @PathVariable UUID contentId) {
        FindContentDetailResponse allContent = contentService.getContent(contentGroupId,contentId);
        return new ResponseEntity<>(ApiResponse.of(request,allContent), HttpStatus.OK);
    }
}

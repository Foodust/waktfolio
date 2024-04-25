package waktfolio.rest.controller.content;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Tag(name = "콘텐츠 생성")
    public ResponseEntity<ApiResponse> createContent(HttpServletRequest request,@ModelAttribute CreateContentRequest createContentRequest){
        contentService.createContent(request, createContentRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Tag(name = "콘텐츠 업데이트")
    public ResponseEntity<ApiResponse> updateContent(HttpServletRequest request,@ModelAttribute UpdateContentRequest updateContentRequest){
        contentService.updateContent(request, updateContentRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @GetMapping("/main")
    @Tag(name = "메인 콘텐츠 가져오기")
    public ResponseEntity<ApiResponse> getMainContent(HttpServletRequest request) {
        FindMainContentResponse main = contentService.getMainMember();
        return new ResponseEntity<>(ApiResponse.of(request, main), HttpStatus.OK);
    }

    @PatchMapping("/{contentId}/like")
    @Tag(name = "콘텐츠 좋아요 누르기")
    public ResponseEntity<ApiResponse> like(HttpServletRequest request, @PathVariable UUID contentId) {
        LikeResponse isLike = contentService.upLikeContent(request, contentId);
        return new ResponseEntity<>(ApiResponse.of(request,isLike), HttpStatus.OK);
    }
    @PatchMapping("/{contentId}/view")
    @Tag(name = "콘텐츠 조회수 증가")
    public ResponseEntity<ApiResponse> view(HttpServletRequest request, @PathVariable UUID contentId) {
        contentService.viewContent(contentId);
        return new ResponseEntity<>(ApiResponse.of(request), HttpStatus.OK);
    }

    @GetMapping("")
    @Tag(name = "그룹 검색하기")
    public ResponseEntity<ApiResponse> getAllContentGroup(HttpServletRequest request, @RequestParam List<String> tags, Pageable pageable){
        List<FindMemberResponse> allContentGroup = contentService.findAllContentGroup(tags,pageable);
        return new ResponseEntity<>(ApiResponse.of(request,allContentGroup), HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    @Tag(name = "그룹 상세조회 하기")
    public ResponseEntity<ApiResponse> getContentGroup(HttpServletRequest request, @PathVariable UUID memberId, List<String> tags) {
        List<FindContentResponse> allContentGroup = contentService.getContentGroup(memberId, tags);
        return new ResponseEntity<>(ApiResponse.of(request,allContentGroup), HttpStatus.OK);
    }
    @GetMapping("/{memberId}/{contentId}")
    @Tag(name = "콘텐트 상세조회 하기")
    public ResponseEntity<ApiResponse> getContent(HttpServletRequest request, @PathVariable UUID memberId, @PathVariable UUID contentId) {
        FindContentDetailResponse allContent = contentService.getContent(memberId,contentId);
        return new ResponseEntity<>(ApiResponse.of(request,allContent), HttpStatus.OK);
    }
}

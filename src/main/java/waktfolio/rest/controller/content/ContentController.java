package waktfolio.rest.controller.content;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.content.ContentService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.content.MainContentResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("/main")
    public ResponseEntity<ApiResponse> getAllContent(HttpServletRequest request){
        MainContentResponse main = contentService.main();
        return new ResponseEntity<>(ApiResponse.of(request,main), HttpStatus.OK);
    }

    @PatchMapping("/{contentId}/like")
    public ResponseEntity<ApiResponse> like(HttpServletRequest request, @PathVariable UUID contentId){
        contentService.upLike(request,contentId);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }

}

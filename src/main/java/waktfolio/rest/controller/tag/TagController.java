package waktfolio.rest.controller.tag;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waktfolio.application.service.tag.TagService;
import waktfolio.rest.ApiResponse;
import waktfolio.rest.dto.content.CreateContentRequest;
import waktfolio.rest.dto.member.RegisterMemberRequest;
import waktfolio.rest.dto.tag.CreateTagRequest;
import waktfolio.rest.dto.tag.FindTagResponse;
import waktfolio.rest.dto.tag.UpdateTagRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    @PostMapping("")
    @Tag(name = "태그 생성")
    public ResponseEntity<ApiResponse> createTag(HttpServletRequest request, @RequestBody CreateTagRequest createTagRequest){
        tagService.createTag(request,createTagRequest);
        return new ResponseEntity<>(ApiResponse.of(request), HttpStatus.OK);
    }
    @GetMapping("")
    @Tag(name = "태그들 가져오기")
    public ResponseEntity<ApiResponse> getTag(HttpServletRequest request){
        List<FindTagResponse> tags = tagService.findTag(request);
        return new ResponseEntity<>(ApiResponse.of(request,tags),HttpStatus.OK);
    }
    @GetMapping("/main")
    @Tag(name ="메인 추천 태그 가져오기")
    public ResponseEntity<ApiResponse> getTags(HttpServletRequest request){
        List<FindTagResponse> tags = tagService.findTags();
        return new ResponseEntity<>(ApiResponse.of(request,tags),HttpStatus.OK);
    }


    @PatchMapping("")
    @Tag(name = "태그 수정하기")
    public ResponseEntity<ApiResponse> updateTag(HttpServletRequest request, @RequestBody UpdateTagRequest updateTagRequest){
        tagService.updateTag(request,updateTagRequest);
        return new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
    @DeleteMapping("/{tagId}")
    @Tag(name = "태그 삭제")
    public ResponseEntity<ApiResponse> deleteTag(HttpServletRequest request, @PathVariable UUID tagId){
        tagService.deleteTag(request,tagId);
        return  new ResponseEntity<>(ApiResponse.of(request),HttpStatus.OK);
    }
}

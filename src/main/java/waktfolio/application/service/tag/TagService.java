package waktfolio.application.service.tag;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.tag.CreateTagRequest;
import waktfolio.rest.dto.tag.FindTagResponse;
import waktfolio.rest.dto.tag.UpdateTagRequest;

import java.util.List;
import java.util.UUID;

public interface TagService {
    void createTag(HttpServletRequest request, CreateTagRequest createTagRequest);
    void deleteTag(HttpServletRequest request, UUID tagId);
    void updateTag(HttpServletRequest request, UpdateTagRequest updateTagRequest);
    List<FindTagResponse> findTag(HttpServletRequest request);
    List<FindTagResponse> findTags();
}

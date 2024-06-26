package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    void createContent(HttpServletRequest request,CreateContentRequest createContentRequest);
    void updateContent(HttpServletRequest request,UpdateContentRequest updateContentRequest);
    FindMainContentResponse getMainMember();
    FindAllContentResponse findAllContentGroup(List<String> tags, Pageable pageable);
    List<FindContentResponse> getContentGroup(UUID memberId);
    FindContentDetailResponse getContent(HttpServletRequest request, UUID contentId);
    ViewResponse viewContent(UUID contentId);
    LikeResponse upLikeContent(HttpServletRequest request, UUID contentId);
}

package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    void createContent(HttpServletRequest request,CreateContentRequest createContentRequest);
    FindMainContentResponse getMainMember();
    List<FindMemberResponse> findAllContentGroup(List<String> tags, Pageable pageable);
    List<FindContentResponse> getContentGroup(UUID contentGroupId, List<String> tag);
    FindContentDetailResponse getContent(UUID contentGroupId, UUID contentId);
    void viewContent(UUID contentId);
    LikeResponse upLikeContent(HttpServletRequest request, UUID contentId);
}

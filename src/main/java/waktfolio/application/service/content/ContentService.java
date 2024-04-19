package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    MainContentResponse getMainContent();
    FindContentGroupResponse getAllContentGroup(List<String> tags);
    List<FindContentDetail> getAllContent(UUID contentGroupId);
    LikeResponse upLikeContent(HttpServletRequest request, UUID contentId);
}

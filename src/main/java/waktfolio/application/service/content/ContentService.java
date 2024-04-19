package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import waktfolio.rest.dto.BaseListDto;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    MainContentResponse getMainContent();
    List<FindContentGroupResponse> getAllContentGroup(List<String> tags);
    List<FindContentDetail> getAllContent(UUID contentGroupId,List<String> tag);
    LikeResponse upLikeContent(HttpServletRequest request, UUID contentId);
}

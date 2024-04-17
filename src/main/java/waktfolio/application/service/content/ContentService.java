package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import waktfolio.rest.dto.content.MainContentResponse;

import java.util.UUID;

public interface ContentService {
    MainContentResponse main();

    void upLike(HttpServletRequest request, UUID contentId);


}

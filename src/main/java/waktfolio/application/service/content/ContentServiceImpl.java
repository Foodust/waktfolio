package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.DayLikeRepository;
import waktfolio.domain.repository.view.DayViewRepository;
import waktfolio.rest.dto.content.MainContentResponse;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;
    private final DayLikeRepository dayLikeRepository;
    private final DayViewRepository dayViewRepository;
    @Override
    public MainContentResponse main() {
        return null;
    }

    @Override
    public void upLike(HttpServletRequest request, UUID contentId) {

    }


    @Scheduled(cron = "0 0 0 * * *")
    private void getDayLike(){

    }
    @Scheduled(cron = "0 0 0 * * *")
    private void getDayView(){

    }
}

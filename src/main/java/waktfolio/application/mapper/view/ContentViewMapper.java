package waktfolio.application.mapper.view;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.view.ContentView;

import java.util.UUID;

@Component
public class ContentViewMapper {
    public ContentView contentView(UUID contentId){
        return ContentView.builder()
                .contentId(contentId)
                .viewCount(0L)
                .build();
    }
}

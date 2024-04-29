package waktfolio.application.mapper.count;

import org.springframework.stereotype.Component;
import waktfolio.rest.dto.content.ViewResponse;
import waktfolio.rest.dto.log.Count;

@Component
public class CountMapper {
    public ViewResponse viewResponseOf(Long totalViewCount){
        return ViewResponse.builder()
                .totalViewCount(totalViewCount)
                .build();
    }
}

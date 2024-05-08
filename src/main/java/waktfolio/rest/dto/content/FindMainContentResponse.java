package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindMainContentResponse {
    List<FindContent> mainContent;
    List<FindContent> newContent;
    List<FindContent> likeContent;
    List<FindContent> viewContent;
}

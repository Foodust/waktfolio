package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainContentResponse {
    List<FindContentGroup> mainContentGroups;
    List<FindContentGroup> newContentGroups;
    List<FindContentGroup> likeContentGroups;
    List<FindContentGroup> viewContentGroups;
}

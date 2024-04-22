package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMainContentResponse {
    List<FindContent> mainMember;
    List<FindContent> newMember;
    List<FindContent> likeMember;
    List<FindContent> viewMember;
}

package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainContentResponse {
    List<FindMember> mainMember;
    List<FindMember> newMember;
    List<FindMember> likeMember;
    List<FindMember> viewMember;
}

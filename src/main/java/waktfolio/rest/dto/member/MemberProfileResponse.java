package waktfolio.rest.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileResponse {
    private String name;
    private String profileImagePath;
    private Long totalLike;
    private Long totalView;
}

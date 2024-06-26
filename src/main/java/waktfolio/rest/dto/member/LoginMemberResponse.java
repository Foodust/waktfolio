package waktfolio.rest.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberResponse {
    private String accessToken;
    private String name;
    private String profileImagePath;
}

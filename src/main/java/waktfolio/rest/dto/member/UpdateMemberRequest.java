package waktfolio.rest.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequest {
    private String loginId;
    private String password;
    private String name;
    private String profileImagePath;
    private List<String> tags;
}

package waktfolio.rest.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@AllArgsConstructor
public class UpdateMemberRequest {
    @Length(min = 4, max = 12)
    private String loginId;
    @Length(min = 4, max = 15)
    private String password;
    @Length(min = 2, max = 12)
    private String name;
    private MultipartFile profileImage;
}

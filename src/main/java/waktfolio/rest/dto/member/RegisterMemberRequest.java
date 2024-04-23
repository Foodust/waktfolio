package waktfolio.rest.dto.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public class RegisterMemberRequest implements Serializable {

    @NotNull
    @NotEmpty
    @Length(min = 4, max = 12)
    private String loginId;
    @NotNull
    @NotEmpty
    @Length(min = 4, max = 15)
    private String password;
    @NotNull
    @NotEmpty
    @Length(min = 2, max = 12)
    private String name;
    private MultipartFile profileImage;
}

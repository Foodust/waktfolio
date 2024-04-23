package waktfolio.rest.dto.content;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContentRequest {
    @NotEmpty
    @NotNull
    @Length(min = 1,max = 20)
    private String name;
    @Length(min = 1,max = 50)
    private String description;
    private MultipartFile thumbnailImage;
    @NotEmpty
    @NotNull
    private MultipartFile object;
    private MultipartFile backGround;
    private String backGroundColorCode;
    @NotEmpty
    @NotNull
    @Length(min = 1,max = 20)
    private String tagName;
    private String youtubeLink;
    private String cafeLink;
}

package waktfolio.rest.dto.content;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateContentRequest implements Serializable {
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
    private UUID tagId;
    private String youtubeLink;
    private String cafeLink;
}

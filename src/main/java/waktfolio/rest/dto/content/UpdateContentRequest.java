package waktfolio.rest.dto.content;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateContentRequest implements Serializable {
    @NotEmpty
    @NotNull
    private UUID contentId;

    @Length(min = 1,max = 20)
    private String name;
    @Length(min = 1,max = 50)
    private String description;
    private MultipartFile thumbnailImage;
    private MultipartFile object;
    private MultipartFile backGround;
    private String backGroundColorCode;
    @Length(min = 1,max = 20)
    private String tagName;
    private String youtubeLink;
    private String cafeLink;
}

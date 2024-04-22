package waktfolio.rest.dto.content;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContentRequest {
    private String name;
    private String description;
    private String thumbnailImagePath;
    private String objectPath;
    private String backGroundPath;
    private String backGroundColorCode;
    private String tag;
    private String youtubeLink;
    private String cafeLink;
}

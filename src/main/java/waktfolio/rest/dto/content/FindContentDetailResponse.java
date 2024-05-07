package waktfolio.rest.dto.content;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindContentDetailResponse {
    private UUID contentId;
    private String name;
    private String objectPath;
    private String description;
    private String thumbnailImagePath;
    private String backGroundPath;
    private String backGroundColorCode;
    private Long likes;
    private Long views;
    private String tagName;
    private String youtubeLink;
    private String cafeLink;
    private Boolean isLike;
}

package waktfolio.rest.dto.content;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindContent {
    private UUID contentId;
    private String memberName;
    private String name;
    private String thumbnailImagePath;
    private Long likes;
    private Long views;
}

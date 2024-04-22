package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindContentResponse {
    private UUID contentId;
    private String name;
    private String description;
    private String thumbnailImagePath;
    private Long likes;
    private Long views;
}

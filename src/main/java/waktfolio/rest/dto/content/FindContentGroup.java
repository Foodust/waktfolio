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
public class FindContentGroup {
    private UUID contentGroupId;
    private String contentGroupName;
    private String thumbnailImagePath;
    private Long likes;
    private Long views;
}

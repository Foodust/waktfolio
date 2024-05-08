package waktfolio.rest.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindMemberResponse {
    private UUID memberId;
    private String memberName;
    private String thumbnailImagePath;
    private Long likes;
    private Long views;
}

package waktfolio.rest.dto.content;

import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private Long totalLikeCount;
    private Boolean isLike;
}

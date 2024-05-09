package waktfolio.domain.repository.like;

import java.math.BigDecimal;
import java.util.UUID;

public interface MemberLikeCustomRepository {
    Long countAddCountByContentId(UUID contentId);
    Long countAddCountByMemberId(UUID memberId);
}

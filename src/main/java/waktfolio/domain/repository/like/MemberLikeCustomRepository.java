package waktfolio.domain.repository.like;

import java.math.BigDecimal;
import java.util.UUID;

public interface MemberLikeCustomRepository {
    BigDecimal countAddCountByContentId(UUID contentId);
    Long countAddCountByMemberId(UUID memberId);
}

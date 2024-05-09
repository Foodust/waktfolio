package waktfolio.domain.repository.view;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public interface ContentViewCustomRepository {
    BigDecimal sumAddSumByContentId(UUID contentId);
    Long sumAddSumByMemberId(UUID memberId);
}

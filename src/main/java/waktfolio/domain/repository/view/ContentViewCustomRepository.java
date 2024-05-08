package waktfolio.domain.repository.view;

import java.util.UUID;

public interface ContentViewCustomRepository {
    Long sumAddSumByContentId(UUID contentId);
    Long sumAddSumByMemberId(UUID memberId);
}

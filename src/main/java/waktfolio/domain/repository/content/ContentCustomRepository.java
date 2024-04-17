package waktfolio.domain.repository.content;

import java.util.UUID;

public interface ContentCustomRepository {
    Long sumLikeByMemberId(UUID memberId);
    Long sumViewByMemberId(UUID memberId);

}

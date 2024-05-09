package waktfolio.domain.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.like.QMemberLike;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberLikeCustomRepositoryImpl implements MemberLikeCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QMemberLike memberLike = QMemberLike.memberLike;
    private final QDayLike dayLike = QDayLike.dayLike;
    private final QContent content = QContent.content;
    private final EntityManager entityManager;

    @Override
    public BigDecimal countAddCountByContentId(UUID contentId) {
        String sqlQuery = "SELECT (COUNT(dl.id) + COUNT(ml.id)) " +
                "FROM day_like dl " +
                "JOIN member_like ml ON ml.content_id = dl.content_id " +
                "WHERE ml.content_id = :contentId";
        return (BigDecimal) entityManager
                .createNativeQuery(sqlQuery)
                .setParameter("contentId", contentId)
                .getSingleResult();
    }

    @Override
    public Long countAddCountByMemberId(UUID memberId) {
        return queryFactory
                .select(
                        dayLike.count().add(memberLike.count())
                )
                .from(content)
                .leftJoin(dayLike).on(dayLike.contentId.eq(content.id))
                .leftJoin(memberLike).on(memberLike.contentId.eq(content.id))
                .where(
                        content.memberId.eq(memberId)
                )
                .fetchOne();
    }


}

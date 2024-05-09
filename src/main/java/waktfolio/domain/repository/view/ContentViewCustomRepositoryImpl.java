package waktfolio.domain.repository.view;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QContentView;
import waktfolio.domain.entity.view.QDayView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentViewCustomRepositoryImpl implements ContentViewCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QDayView dayView = QDayView.dayView;
    private final QContentView contentView = QContentView.contentView;
    private final QMember member = QMember.member;
    private final QContent content = QContent.content;
    private final EntityManager entityManager;

    @Override
    public BigDecimal sumAddSumByContentId(UUID contentId) {
        String sqlQuery = "SELECT COALESCE(SUM(dv.view_count), 0) + COALESCE(SUM(cv.view_count), 0) " +
                "FROM day_view dv " +
                "FULL OUTER JOIN content_view cv ON cv.content_id = dv.content_id " +
                "WHERE dv.content_id = :contentId";
        return (BigDecimal) entityManager
                .createNativeQuery(sqlQuery)
                .setParameter("contentId", contentId)
                .getSingleResult();
    }

    @Override
    public Long sumAddSumByMemberId(UUID memberId) {
        return queryFactory
                .select(
                        dayView.viewCount.sum().coalesce(0L).add(contentView.viewCount.sum().coalesce(0L))
                )
                .from(content)
                .leftJoin(dayView).on(dayView.contentId.eq(content.id))
                .leftJoin(contentView).on(contentView.contentId.eq(content.id))
                .where(
                        content.memberId.eq(memberId)
                )
                .fetchOne();
    }
}

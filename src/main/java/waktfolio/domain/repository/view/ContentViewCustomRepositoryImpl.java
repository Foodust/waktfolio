package waktfolio.domain.repository.view;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QContentView;
import waktfolio.domain.entity.view.QDayView;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentViewCustomRepositoryImpl implements ContentViewCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QDayView dayView = QDayView.dayView;
    private final QContentView contentView = QContentView.contentView;
    private final QMember member = QMember.member;
    private final QContent content = QContent.content;

    @Override
    public Long sumAddSumByContentId(UUID contentId) {
        return queryFactory
                .select(
                        dayView.viewCount.sum().coalesce(0L).add(contentView.viewCount.sum().coalesce(0L))
                )
                .from(dayView)
                .leftJoin(contentView).on(contentView.contentId.eq(contentId))
                .where(
                        dayView.contentId.eq(contentId)
                )
                .fetchOne();
    }

    @Override
    public Long sumAddSumByMemberId(UUID memberId) {
        return queryFactory
                .select(
                        dayView.viewCount.sum().coalesce(0L).add(contentView.viewCount.sum().coalesce(0L))
                )
                .from(dayView)
                .join(content).on(content.memberId.eq(memberId))
                .leftJoin(contentView).on(contentView.contentId.eq(content.id))
                .where(
                        dayView.contentId.eq(content.id)
                )
                .fetchOne();
    }
}

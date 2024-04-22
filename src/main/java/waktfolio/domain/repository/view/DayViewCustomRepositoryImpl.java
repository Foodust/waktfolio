package waktfolio.domain.repository.view;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QDayView;
import waktfolio.rest.dto.content.FindContent;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayViewCustomRepositoryImpl implements DayViewCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QDayView dayView = QDayView.dayView;
    private final QMember member = QMember.member;
    private final QContent content = QContent.content;

    @Override
    public List<FindContent> findOrderByViewCount() {
        return queryFactory
                .select(
                        Projections.bean(FindContent.class,
                                dayView.contentId,
                                member.name.as("memberName"),
                                content.name,
                                content.thumbnailImagePath,
                                dayView.viewCount.as("views")
                        )
                )
                .from(dayView)
                .join(content).on(content.id.eq(dayView.contentId))
                .join(member).on(member.id.eq(content.memberId))
                .groupBy(
                        dayView.contentId
                )
                .orderBy(
                        dayView.viewCount.desc()
                )
                .limit(5)
                .fetch();
    }
}

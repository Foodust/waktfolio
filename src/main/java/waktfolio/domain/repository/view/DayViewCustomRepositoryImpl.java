package waktfolio.domain.repository.view;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QDayView;
import waktfolio.rest.dto.FindCount;
import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.log.Count;

import java.util.List;
import java.util.UUID;

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
                .select(Projections.bean(FindContent.class,
                        content.id.as("contentId"),
                        member.name.as("memberName"),
                        content.name,
                        content.thumbnailImagePath,
                        getDayView(content.id,"views")
                ))
                .from(content)
                .where(
                        isUseYn()
                )
                .limit(5)
                .join(member).on(member.id.eq(content.memberId))
                .fetch();
    }

    @Override
    public List<Count> countAllView() {
        return queryFactory
                .select(Projections.constructor(Count.class,
                        dayView.contentId,
                        dayView.count()
                ))
                .from(dayView)
                .groupBy(dayView.contentId)
                .fetch();
    }

    private BooleanExpression isUseYn(){
        return content.useYn.eq(true);
    }
    public Expression<Long> getDayView(ComparablePath<UUID> contentId, String name) {
        return ExpressionUtils.as(JPAExpressions.select(dayView.viewCount).from(dayView).where(dayView.contentId.eq(contentId)), name);
    }
}

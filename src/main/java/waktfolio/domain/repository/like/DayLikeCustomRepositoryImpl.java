package waktfolio.domain.repository.like;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.rest.dto.FindCount;
import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.log.Count;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DayLikeCustomRepositoryImpl implements DayLikeCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QDayLike dayLike = QDayLike.dayLike;
    private final QContent content = QContent.content;
    private final QMember member = QMember.member;



    @Override
    public List<FindContent> findOrderByLikeCount() {
        return queryFactory
                .select(Projections.bean(FindContent.class,
                        content.id.as("contentId"),
                        member.name.as("memberName"),
                        content.name,
                        content.thumbnailImagePath,
                        getDayLike(content.id,"likes")
                        ))
                .from(content)
                .where(
                        isUseYn()
                )
                .join(member).on(member.id.eq(content.memberId))
                .fetch();
    }

    @Override
    public List<Count> countAllLike() {
        return queryFactory
                .select(Projections.constructor(Count.class,
                        dayLike.contentId,
                        dayLike.count()
                        ))
                .from(dayLike)
                .groupBy(dayLike.contentId)
                .fetch();
    }

    private BooleanExpression isUseYn(){
        return content.useYn.eq(true);
    }
    public Expression<Long> getDayLike(ComparablePath<UUID> contentId, String name) {
        return ExpressionUtils.as(JPAExpressions.select(dayLike.count()).from(dayLike).where(dayLike.contentId.eq(contentId)), name);
    }
}

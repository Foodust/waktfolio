package waktfolio.domain.repository.like;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.rest.dto.content.FindContent;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayLikeCustomRepositoryImpl implements DayLikeCustomRepository{
    private final JPAQueryFactory queryFactory;
    private final QDayLike dayLike = QDayLike.dayLike;
    private final QContent content = QContent.content;
    private final QMember member = QMember.member;

    @Override
    public List<FindContent> findOrderByLikeCount() {
        return queryFactory
                .select(Projections.bean(FindContent.class,
                        dayLike.contentId,
                        member.name.as("memberName"),
                        content.name,
                        dayLike.count()
                        ))
                .from()
                .join(member).on(member.id.eq(dayLike.memberId))
                .join(content).on(content.id.eq(dayLike.contentId))
                .orderBy(
                        dayLike.count().desc()
                )
                .limit(5)
                .fetch();
    }
}

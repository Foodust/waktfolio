package waktfolio.domain.repository.content;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QContentView;
import waktfolio.domain.entity.view.QDayView;
import waktfolio.rest.dto.content.FindContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QContent content = QContent.content;
    private final QMember member = QMember.member;
    private final QMemberLike memberLike = QMemberLike.memberLike;
    private final QDayLike dayLike = QDayLike.dayLike;
    private final QContentView contentView = QContentView.contentView;
    private final QDayView dayView = QDayView.dayView;

    @Override
    public Long countAddCountByMemberId(UUID memberId) {
        return queryFactory
                .select(
                        memberLike.count().add(dayLike.count())
                )
                .from(memberLike)
                .join(content).on(content.id.eq(memberLike.contentId))
                .join(dayLike).on(dayLike.contentId.eq(memberLike.contentId))
                .where(
                        isMemberId(memberId),
                        isUseYn()
                )
                .fetchOne();
    }
    @Override
    public Optional<Long> sumViewByMemberId(UUID memberId) {
        return Optional.ofNullable(queryFactory
                .select(
                        contentView.viewCount.sum().add(dayView.viewCount.sum())
                )
                .from(contentView)
                .join(content).on(content.id.eq(contentView.contentId))
                .join(dayView).on(dayView.contentId.eq(contentView.contentId))
                .where(
                        isMemberId(memberId),
                        isUseYn()
                )
                .fetchOne());
    }
    @Override
    public List<Content> findByTagLikeIn(List<String> tags, Pageable pageable) {
        BooleanBuilder isTags = new BooleanBuilder();
        BooleanBuilder isNames = new BooleanBuilder();
        BooleanBuilder isMemberNames = new BooleanBuilder();
        for (String tag : tags) {
            isTags.or(content.tagName.containsIgnoreCase(tag));
            isNames.or(content.name.containsIgnoreCase(tag));
            isMemberNames.or(member.name.containsIgnoreCase(tag));
        }
        return queryFactory
                .selectFrom(content)
                .join(member).on(member.id.eq(content.memberId))
                .where(
                        isTags.or(isNames.or(isMemberNames)),
                        isUseYn()
                )
                .orderBy(
                        content.createDate.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<FindContent> findOrderByCreateDate() {
        return queryFactory
                .select(Projections.bean(FindContent.class,
                        content.id.as("contentId"),
                        member.name.as("memberName"),
                        content.name,
                        content.thumbnailImagePath
                ))
                .from(content)
                .join(member).on(member.id.eq(content.memberId))
                .where(
                        isUseYn()
                )
                .orderBy(
                        content.createDate.desc()
                )
                .limit(5)
                .fetch();
    }

    @Override
    public List<Content> findByUseYn(Boolean useYn, Pageable pageable) {
        return queryFactory
                .selectFrom(content)
                .where(
                        content.useYn.eq(false)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isUseYn() {
        return content.useYn.eq(true);
    }

    private BooleanExpression isMemberId(UUID memberId) {
        return memberId != null ? content.memberId.eq(memberId) : null;
    }
}

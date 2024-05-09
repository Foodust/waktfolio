package waktfolio.domain.repository.content;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.content.QTag;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QContentView;
import waktfolio.domain.entity.view.QDayView;
import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.content.FindContentDetail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QContent content = QContent.content;
    private final QMember member = QMember.member;
    private final QTag tag = QTag.tag;

    @Override
    public List<Content> findByMemberIdAndUseYnOrderByTagNameAscCreateDateDesc(UUID memberId, Boolean useYn) {
        return queryFactory
                .selectFrom(content)
                .join(tag).on(tag.id.eq(content.tagId))
                .where(
                        isMemberId(memberId),
                        isUseYn(useYn)
                )
                .orderBy(tag.name.asc())
                .fetch();
    }

    @Override
    public Optional<FindContentDetail> findByIdAndUseYn(UUID contentId, Boolean useYn) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.bean(FindContentDetail.class,
                                content.id,
                                content.name,
                                content.objectPath,
                                content.description,
                                content.thumbnailImagePath,
                                content.backGroundPath,
                                content.backGroundColorCode,
                                getTag(content.tagId, "tagName"),
                                content.youtubeLink,
                                content.cafeLink
                        ))
                        .from(content)
                        .where(
                                isContentId(contentId),
                                isUseYn(useYn)
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<Content> findByTagLikeIn(List<String> tags, Pageable pageable) {
        BooleanBuilder isTags = new BooleanBuilder();
        BooleanBuilder isNames = new BooleanBuilder();
        BooleanBuilder isMemberNames = new BooleanBuilder();
        for (String temp : tags) {
            isTags.or(tag.name.containsIgnoreCase(temp));
            isNames.or(content.name.containsIgnoreCase(temp));
            isMemberNames.or(member.name.containsIgnoreCase(temp));
        }
        return queryFactory
                .selectFrom(content)
                .join(member).on(member.id.eq(content.memberId))
                .join(tag).on(tag.id.eq(content.tagId))
                .where(
                        isTags.or(isNames.or(isMemberNames)),
                        isUseYn(true)
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
                        isUseYn(true)
                )
                .orderBy(
                        content.createDate.desc()
                )
                .limit(5)
                .fetch();
    }

    @Override
    public List<FindContentDetail> findByUseYn(Boolean useYn, Pageable pageable) {
        return queryFactory
                .select(Projections.bean(FindContentDetail.class,
                        content.id,
                        content.name,
                        content.objectPath,
                        content.description,
                        content.thumbnailImagePath,
                        content.backGroundPath,
                        content.backGroundColorCode,
                        getTag(content.tagId, "tagName"),
                        content.youtubeLink,
                        content.cafeLink
                ))
                .from(content)
                .where(
                        isUseYn(useYn)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isUseYn(Boolean useYn) {
        return content.useYn.eq(useYn);
    }

    private BooleanExpression isContentId(UUID contentId) {return content.id.eq(contentId);}
    private BooleanExpression isMemberId(UUID memberId) {return content.memberId.eq(memberId);}

    public Expression<String> getTag(ComparablePath<UUID> code, String name) {
        return ExpressionUtils.as(JPAExpressions.select(tag.name).from(tag).where(tag.id.eq(code)), name);
    }
}

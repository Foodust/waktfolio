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
import waktfolio.domain.entity.like.QMemberLike;
import waktfolio.domain.entity.member.QMember;
import waktfolio.domain.entity.view.QContentView;
import waktfolio.rest.dto.content.FindContent;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QContent content = QContent.content;
    private final QMember member = QMember.member;
    private final QMemberLike memberLike = QMemberLike.memberLike;
    private final QContentView contentView = QContentView.contentView;

    @Override
    public Long sumViewByMemberId(UUID memberId) {
        return queryFactory
                .select(contentView.viewCount.sum())
                .from(contentView)
                .join(content).on(content.id.eq(contentView.contentId))
                .where(
                        isMemberId(memberId),
                        isUseYn()
                )
                .fetchOne();
    }

    @Override
    public List<Content> findByTagLikeIn(List<String> tags, Pageable pageable) {
        BooleanBuilder isTags = new BooleanBuilder();
        for (String tag : tags) {
            isTags.or(content.tagName.containsIgnoreCase(tag));
        }
        return queryFactory
                .selectFrom(content)
                .where(
                        isTags,
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
                        member.name,
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

    private BooleanExpression isUseYn(){
        return content.useYn.eq(true);
    }
    private BooleanExpression isMemberId(UUID memberId) {
        return memberId != null ? content.memberId.eq(memberId) : null;
    }
}

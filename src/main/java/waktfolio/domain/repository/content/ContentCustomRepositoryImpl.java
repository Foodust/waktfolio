package waktfolio.domain.repository.content;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.content.QContent;
import waktfolio.rest.dto.BaseListDto;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QContent content = QContent.content;

    @Override
    public Long sumViewByMemberId(UUID memberId) {
        return queryFactory
                .select(content.views.sum())
                .from(content)
                .where(
                        isMemberId(memberId)
                )
                .fetchOne();
    }

    @Override
    public List<Content> findByTagLikeIn(List<String> tags,Pageable pageable) {
        BooleanBuilder isTags = new BooleanBuilder();
        for (String tag : tags) {
            isTags.or(content.tag.containsIgnoreCase(tag));
        }
        return queryFactory
                .selectFrom(content)
                .where(
                        isTags
                )
                .orderBy(
                        content.createDate.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isMemberId(UUID memberId) {
        return memberId != null ? content.memberId.eq(memberId) : null;
    }

    private BooleanExpression isContentGroupId(UUID contentGroupId) {
        return contentGroupId != null ? content.contentGroupId.eq(contentGroupId) : null;
    }
}

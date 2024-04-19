package waktfolio.domain.repository.content;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QContent content = QContent.content;
    @Override
    public Long sumLikeByMemberId(UUID memberId) {
        return queryFactory
                .select(content.likes.sum())
                .from(content)
                .where(
                    isMemberId(memberId)
                )
                .fetchOne();
    }

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

    private BooleanExpression isMemberId(UUID memberId){
        return memberId != null ?  content.memberId.eq(memberId) : null;
    }
}

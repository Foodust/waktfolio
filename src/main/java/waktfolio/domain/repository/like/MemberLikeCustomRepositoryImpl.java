package waktfolio.domain.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import waktfolio.domain.entity.content.QContent;
import waktfolio.domain.entity.like.QDayLike;
import waktfolio.domain.entity.like.QMemberLike;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberLikeCustomRepositoryImpl implements MemberLikeCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QMemberLike memberLike = QMemberLike.memberLike;
    private final QDayLike dayLike = QDayLike.dayLike;
    private final QContent content = QContent.content;

}

package waktfolio.application.service.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.member.MemberMapper;
import waktfolio.domain.entity.Member;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.member.*;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {
        Member member = memberRepository.findByLoginId(loginMemberRequest.getLoginId()).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        if(!passwordEncoder.matches(member.getPassword(),loginMemberRequest.getPassword())){
            throw BusinessException.DOES_NOT_MATCH_PASSWORD();
        }
        String newToken = jwtTokenUtil.generateToken(member.getId());
        return memberMapper.loginMemberResponseOf(member.getName(),newToken);
    }

    @Override
    public void update(HttpServletRequest request, UpdateMemberRequest updateMemberRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        memberMapper.memberUpdateFrom(member,updateMemberRequest);
    }

    @Override
    public void register(RegisterMemberRequest registerMemberRequest) {
        memberRepository.findByLoginId(registerMemberRequest.getLoginId()).ifPresent( a->{throw BusinessException.ALREADY_EXISTS_USER_ID();});
        String encodePassword = passwordEncoder.encode(registerMemberRequest.getPassword());
        Member member = memberMapper.memberFrom(registerMemberRequest,encodePassword);
        memberRepository.save(member);
    }

    @Override
    public MemberProfileResponse profile(HttpServletRequest request) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        Long totalLike = contentRepository.sumLikeByMemberId(memberId);
        Long totalView = contentRepository.sumViewByMemberId(memberId);
        return memberMapper.memberProfileResponseOf(member,totalLike,totalView);
    }
}

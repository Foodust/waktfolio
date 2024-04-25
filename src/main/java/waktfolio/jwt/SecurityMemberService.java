package waktfolio.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.exception.BusinessException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityMemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository
                .findById(UUID.fromString(id))
                .map(SecurityMember::new)
                .orElseThrow(BusinessException::NOT_FOUND_MEMBER);
    }
}

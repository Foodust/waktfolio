package waktfolio.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import waktfolio.domain.entity.member.Member;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SecurityMember implements UserDetails {
    private final Member member;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(member.getMemberPermission().toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

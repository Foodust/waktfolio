package waktfolio.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import waktfolio.domain.entity.member.MemberPermission;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig {
    private final SecurityMemberService securityMemberService;
    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(new JwtSecurityFilter(jwtTokenUtil, securityMemberService), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/member",
                                "/api/member/**",
                                "/api/content",
                                "/api/tag"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/member",
                                "/api/member/**",
                                "/api/content",
                                "/api/content/**",
                                "/api/tag",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/member",
                                "/api/member/**",
                                "/api/content/**",
                                "/api/tag"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/member",
                                "/api/member/**",
                                "/api/content",
                                "/api/content**",
                                "/api/tag/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/aws",
                                "/api/aws/**",
                                "/api/admin",
                                "/api/admin/**",
                                "/api/aws/**",
                                "/api/content",
                                "/api/content/**",
                                "/api/member",
                                "/api/member/**",
                                "/api/tag",
                                "/api/tag/**"
                        ).hasAuthority(MemberPermission.ADMIN.name())
                );
        return http.build();
    }
}

package waktfolio.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import waktfolio.exception.ApplicationErrorCode;
import waktfolio.exception.ErrorResponse;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtTokenUtil tokenUtil;

    public void setErrorResponse(HttpServletResponse response, HttpServletRequest request, ApplicationErrorCode ex) {
        try {
            response.setStatus(ex.getStatus());
            response.setContentType("application/json; charset=UTF-8");
            ErrorResponse errorResponse = ErrorResponse.of(request, ex, ex.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null, memberId = null;
        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                token = requestTokenHeader.replace("Bearer ", "");
                memberId = tokenUtil.getSubjectFromToken(token);
            } else {
                log.warn("Token 이 존재하지 않습니다.");
            }
            if (memberId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (tokenUtil.validateToken(token, memberId)) {
                    throw new Exception();
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, request, ApplicationErrorCode.EXPIRED_JWT_TOKEN);
        } catch (SignatureException e) {
            setErrorResponse(response, request, ApplicationErrorCode.INVALID_TOKEN);
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}

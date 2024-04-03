package waktfolio.backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil {

    private final JwtProperties jwtProperties;
    private final SecretKey key;
    public final Long accessTokenTime = 1000L * 60 * 60 * 2;
    public JwtTokenUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }


    public String getSubjectFromToken(String token ) {
        return getFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UUID memberId) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,memberId.toString(), accessTokenTime);
    }
    public String generateRefreshToken(Long time) {
        return Jwts
                .builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public Boolean validateToken(String token, String id ) {
        return (getSubjectFromToken(token).equals(id)) && !isTokenExpired(token);
    }

    public <R> R getFromToken(String token, Function<Claims, R> claimsResolver ) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public String getStringFromToken(String token, String tk){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get(tk).toString();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String doGenerateToken(Map<String, Object> claims, String id, Long time) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String getStringFromHeader(HttpServletRequest request, String tag){
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.replace("Bearer ", "");
            return getStringFromToken(token,tag);
        }
        return token;
    }
    public String getSubjectFromHeader(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String token = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.replace("Bearer ", "");
            return getSubjectFromToken(token);
        }
        return token;
    }
}

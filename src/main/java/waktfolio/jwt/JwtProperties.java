package waktfolio.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String secret;
    private Integer validity;
    private String authoritiesKey;
}



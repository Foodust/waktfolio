package waktfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import waktfolio.jwt.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
public class WaktfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaktfolioApplication.class, args);
	}

}

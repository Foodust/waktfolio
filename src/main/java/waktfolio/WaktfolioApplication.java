package waktfolio;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import waktfolio.jwt.JwtProperties;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
@OpenAPIDefinition(servers = {
		@Server(url = "/")
})
public class WaktfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaktfolioApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}

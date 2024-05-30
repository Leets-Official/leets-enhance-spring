package leets.enhance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
/**
 * TODO 1. 액세스 토큰을 가지고 인가 처리
 */
public class EnhanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnhanceApplication.class, args);
	}

}

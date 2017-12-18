package votingsystem.menuvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import votingsystem.menuvote.config.JpaConfig;

@SpringBootApplication
public class MenuVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {MenuVoteApplication.class, JpaConfig.class}, args);
	}
}

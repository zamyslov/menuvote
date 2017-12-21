package votingsystem.menuvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import votingsystem.menuvote.config.JpaConfig;

@SpringBootApplication
public class MenuVoteApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MenuVoteApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MenuVoteApplication.class);
	}
}
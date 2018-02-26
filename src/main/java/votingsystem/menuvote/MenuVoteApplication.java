package votingsystem.menuvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import votingsystem.menuvote.config.JpaConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"votingsystem.menuvote"})
public class MenuVoteApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MenuVoteApplication.class, args);
		String password = "admin";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		System.out.println(hashedPassword);
	}
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(MenuVoteApplication.class);
//	}
}
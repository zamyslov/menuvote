package votingsystem.menuvote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.accept.ContentNegotiationManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // authenticated user allow to access /**
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").access("isAnonymous()")
                .antMatchers("/register").access("isAnonymous()")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/**").access("isAuthenticated()")
                .anyRequest().authenticated()
                .and()
                .formLogin().failureForwardUrl("/login?error=true")
                .and()
                .logout().logoutSuccessUrl("/login");
    }

    // create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("User").password("password").roles("USER")
                .and()
                .withUser("Admin").password("admin").roles("ADMIN");
    }

}
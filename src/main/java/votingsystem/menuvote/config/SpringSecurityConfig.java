package votingsystem.menuvote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    // roles admin allow to access /admin/**
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

}
package ru.kwuh.housevote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kwuh.housevote.services.MongoUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MongoUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic().disable();
        security.cors().and().csrf().disable();

        security
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/profile/create", "/profile/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/profile/login")
                .loginProcessingUrl("/profile/login")
                .defaultSuccessUrl("/profile/me", true)
                .and().logout()
                .logoutUrl("/profile/logout")
                .deleteCookies("JSESSIONID");
           /*     .and().httpBasic()
                .and().sessionManagement().disable();

            */
    }
}

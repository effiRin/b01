package org.zerock.b01.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    // deprecated -> 스프링과 시큐리티는 버전이 다름

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override  // generator를 통해서 override method 추가
    protected void configure(HttpSecurity http) throws Exception {
        log.info("-----------------------");
        http.formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        log.info("------------web configure-------------------");

        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // PathRequest import할때 servlet 있는 걸로

    }
}

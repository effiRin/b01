package org.effirin.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.effirin.b01.security.CustomUserDetailsService;
import org.effirin.b01.security.handler.Custom403Handler;

import javax.sql.DataSource;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig  {
    // deprecated -> 스프링과 시큐리티는 버전이 다름

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override  // generator를 통해서 override method 추가
//    protected void configure(HttpSecurity http) throws Exception {   이거 대신에 filterChain 추가

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        log.info("-----------------------");
        http.formLogin().loginPage("/member/login");

        //CSRF 토큰 비활성화
        http.csrf().disable();

        //리멤버미 설정 추가
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30); // 한달동안 리멤버미를 유지하겠다

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); // bean으로 추가

        http.oauth2Login(); // 카카오 로그인

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//        log.info("------------web configure-------------------");
//
//        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//        // PathRequest import할때 servlet 있는 걸로
//
//    } // 이거 지우고 webSecurityCustomizer 추가

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("------------web configure-------------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}

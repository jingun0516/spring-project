package com.estsoft.springproject.user.config;

import com.estsoft.springproject.user.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


// 스프링 시큐리티 설정
@Configuration
public class WebSecurityConfiguration {
    private UserDetailService userDetailService;

    public WebSecurityConfiguration(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // 특정 요청은 스프링 시큐리티 설정을 받지 않도록 ignore 처리
    @Bean
    public WebSecurityCustomizer ignore() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/static/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
    }

    // 특정 요청에 대한 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(  // 3) 인증, 인가 설정
                custom -> custom.requestMatchers("/login", "/signup", "/user").permitAll()
//                        .requestMatchers("/articles/**").hasAuthority("ADMIN")   // 인가 설정
                        .anyRequest().permitAll()   // 실습을 위한 로그인 생략
//                        .anyRequest().authenticated()   // 위에서 설정한 것 이외는 인증을 해야함
                )
                .formLogin(custom -> custom.loginPage("/login")        //4) 폼 기반 로그인 설정
                    .defaultSuccessUrl("/articles"))
                .logout(custom -> custom.logoutSuccessUrl("/login")       // 5) 로그아웃 설정
                        .invalidateHttpSession(true))
                .csrf(custom -> custom.disable())       // 6) csrf 비활성화
                .build();
        /*
        return httpSecurity.authorizeHttpRequests()    // 3) 인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()        //4) 폼 기반 로그인 설정
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout()       // 5) 로그아웃 설정
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()       // 6) csrf 비활성화
                .build();
                */

    }

    // 7) 인증관리자 관련 설정
    // 과거에는 필요했으나 지금은 디폴트로 설정되어 있어 필요 X
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)  // 8) 사용자 정보 서비스 설정
//                .passwordEncoder(bCryptPasswordEncoder)
//                .build();
//    }


    // 패스워드 암호화 방식 정의
    // BCryptPasswordEncoder: security 에서 제공하는 암호화 클래스
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

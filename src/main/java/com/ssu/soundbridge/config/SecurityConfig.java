package com.ssu.soundbridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.soundbridge.jwt.JwtAuthenticationProcessingFilter;
import com.ssu.soundbridge.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.ssu.soundbridge.login.handler.LoginFailureHandler;
import com.ssu.soundbridge.login.handler.LoginSuccessHandler;
import com.ssu.soundbridge.repository.UserRepository;
import com.ssu.soundbridge.service.JwtService;
import com.ssu.soundbridge.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


/*    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csrf disable => jwt로 stateless로 관리할 것이기 때문에 꺼도 됨
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((form) -> form.disable()) // 폼로그인
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/",
                            "/api/users/sign-up","/","/favicon.ico","/login/page"
                                ,"/login/**", "/oauth2/**", "/login/oauth2/**","/api/auth/reissue","/send-email","verify"
                                ,"/swagger-ui/**","/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated() // 위 경로 외 경로는 인증된 사용자만 접근 가능
                );

        //세션 설정 : STATELESS => jwt니까 stateless
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // [PART4]
        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter ->CustomJsonUsernamePasswordAuthenticationFilter-> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class); // b ->  a
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class); // a -> b
        return http.build();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder()); // 비밀번호 비교 방식 설정 (BCrypt 등)
        provider.setUserDetailsService(loginService);   // 사용자 정보 조회 서비스 설정
        return new ProviderManager(provider);           // 이 Provider를 통해 인증 관리
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }
}

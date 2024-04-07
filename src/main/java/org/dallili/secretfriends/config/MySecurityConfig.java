package org.dallili.secretfriends.config;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.jwt.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class MySecurityConfig{

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil JwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/swagger-ui.html", "/members", "/members/login",
            "/v3/api-docs","/api-docs/**","/swagger-resources/**","api-docs/",
            "/v3/api-docs/**"
    };

    //비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }  //정적 자원들을 스프링 시큐리티 적용에서 제외

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                //.formLogin(form->form.disable())
                .csrf(csrf-> csrf.disable())
                .cors(Customizer.withDefaults())
                //JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JwtFilter(customUserDetailsService,JwtUtil), UsernamePasswordAuthenticationFilter.class)
                //exception handling 할 때 만들었던 클래스를 추가
                .exceptionHandling(authenticationManager->authenticationManager
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated());



        return http.build();
    }



}

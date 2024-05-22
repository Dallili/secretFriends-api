package org.dallili.secretfriends.config;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.security.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class MySecurityConfig{

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/swagger-ui.html", "/members/signup/**", "/members/login",
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
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                //JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(new JwtFilter(customUserDetailsService,jwtUtil), UsernamePasswordAuthenticationFilter.class)
                //exception handling 할 때 만들었던 클래스를 추가
                .exceptionHandling(authenticationManager->authenticationManager
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated());



        return http.build();
    }


    // cors 오류 해결
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 리액트 오리진
        config.addAllowedOrigin("http://localhost:3000"); // 로컬 환경
        config.addAllowedOrigin("https://localhost:3000");
        config.addAllowedOrigin("secret-friends.link");
        config.addAllowedOrigin("http://secret-friends.link");
        config.addAllowedOrigin("https://secret-friends.link");
        // 스프링부트 오리진
        config.addAllowedOrigin("http://localhost:8080"); // 로컬 환경
        config.addAllowedOrigin("https://localhost:8080");
        config.addAllowedOrigin("http://localhost:9090"); // 도커랑 8080 포트 겹칠 때 9090 포트 사용
        config.addAllowedOrigin("https://localhost:9090");
        config.addAllowedOrigin("http://ec2-3-17-227-166.us-east-2.compute.amazonaws.com");
        config.addAllowedOrigin("api.secretfriends.shop");
        config.addAllowedOrigin("http://api.secretfriends.shop");
        config.addAllowedOrigin("https://api.secretfriends.shop");

        config.addAllowedMethod("*"); // 모든 메소드 허용.
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



}

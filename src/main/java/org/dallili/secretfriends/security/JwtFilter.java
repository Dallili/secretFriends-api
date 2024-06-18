package org.dallili.secretfriends.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { //요청 받을 때 단 한번만 실행

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    //jwt 토큰 검증 필터 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
           String token = authorizationHeader.substring(7);

           if(jwtUtil.validateToken(token)){
               String email = jwtUtil.getEmail(token);

               UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

               if(userDetails!=null){
                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                           new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               }
           }
        }

        //다음 필터로 넘기기
        filterChain.doFilter(request,response);
    }
}

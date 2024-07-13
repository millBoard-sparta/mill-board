package com.sparta.millboard.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal 메서드 실행");
        String accessToken = request.getHeader("Authorization");
        log.info("doFilterInternal accessToken : " + accessToken);

        validateToken(accessToken);
        log.info("doFilterInternal validateToken(accessToken) : " + accessToken);
        filterChain.doFilter(request, response);

    }

    private void validateToken(String token) {
        log.info("validateToken 메서드 실행. 받은 토큰 : " + token);
        if (token != null && token.startsWith(JwtService.BEARER_PREFIX)) {
            token = token.substring(7);
        }
        log.info("Bearer 접두사를 제거한 token : " + token);
        if (jwtService.isValidToken(token)) {
            Claims claims = jwtService.getClaims(token);
            log.info("validateToken claims : " + claims);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(claims.getSubject());
            log.info("validateToken userDetails : " + userDetails);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

        }
    }
}

package com.sparta.millboard.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j(topic = "JwtService")
@Component
public class JwtService {

    @Value("${jwt.key}")
    private String secret;

    @Value("${jwt.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;

    private Key key;

    public static final String BEARER_PREFIX = "Bearer ";

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 엑세스 토큰 만료시 토큰 재발급
    public String regenerateAccessToken(String refreshToken, String username) {

        if (!isValidToken(refreshToken)) {
            throw new IllegalArgumentException();
        }

        return generateAccessToken(username);

    }

    // 토큰 디코딩
    @PostConstruct
    public void init() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    // 엑세스 토큰
    public String generateAccessToken(String username) {
        return createToken(username, accessExpireTime);

    }

    // 리프레쉬 토큰
    public String generateRefreshToken(String username) {

        return createToken(username, refreshExpireTime);

    }

    // 토큰 생성
    private String createToken(String username, Long expirationTime) {

        Date now = new Date();
        String token = Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + expirationTime))
                        .signWith(key, signatureAlgorithm)
                        .compact();

        log.info("CreateToken 메서드로 생성된 토큰 : " + token);
        return token;
    }

    // 토큰 검증
    public boolean isValidToken(String token) {
        log.info("isValidToken 메서드 실행. 받은 token : " + token);
        try {

            if (token == null) {
                log.error("JWT 토큰이 null 입니다." + token);
                return false;
            }

            if (token.startsWith(BEARER_PREFIX)) {
                token = token.substring(7);
            }

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;

        } catch (JwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;

    }

    // 토큰 만료시간 추출
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    // 토큰에서 사용자 주체를 추출하는 메서드
    public String extractUsername(String token) {
        log.info("extractUsername 메서드 실행 받은 토큰 : " + token);
        return extractClaim(token, Claims::getSubject);

    }

    // 토큰에서 특정 클레임을 추출하는 메서드
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("extractClaim 메서드 실행");
        final Claims claims = extractAllClaims(token);
        log.info("extractClaim 메서드에서 뽑아낸 클래임 : " + claims);
        return claimsResolver.apply(claims);

    }

    // 토큰에서 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        log.info("extractAllClaims 메서드 실행");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(getToken(token))
                .getBody();
    }

    public Claims getClaims(String token) {
        log.info("getClaims 메서드 실행.");
        return extractAllClaims(token);

    }

    // 토큰 가져오기
    public String getToken(String token) {
        log.info("getToken 메서드 실행" + token);
        if (StringUtils.hasText(token)) {
            return token;
        }
        log.error("JWT 토큰 접두사 오류 발생. Token == null 변경." + token);
        return null;

    }
}

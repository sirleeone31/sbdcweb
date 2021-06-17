package com.sbdc.sbdcweb.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sbdc.sbdcweb.security.UserDetailsImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtProvider
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    // sbdcSecretkey(Base64 Encoded)
    @Value("${sbdc.app.jwtSecret}")
    private String jwtSecret;

    // expiration time
    @Value("${sbdc.app.jwtExpiration}")
    private int jwtExpiration;

    /**
     * generateJwtToken 함수
     * 인증철자를 완료한 Jwt 토큰 가져오기
     * 
     * @param   authentication  Authentication 객체
     * @return  Jwt 토큰 값(ID + 생성날짜 + 만료일자 + Secret 키 = Jwt 토큰값)
     */
    public String generateJwtToken(Authentication authentication) {
    	String jwts = null;

    	UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        if (principal.getMemberKey() > 0L) {
        	jwts = Jwts.builder()
                    .setSubject(principal.getUsername())
                    .setId(String.valueOf(principal.getMemberKey()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();
        } else if (principal.getAdminKey() > 0L) {
        	jwts = Jwts.builder()
                    .setSubject(principal.getUsername())
                    .setId(String.valueOf(principal.getAdminKey()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();        	
        }

        return jwts;
    }

    /**
     * validateJwtToken 함수
     * Jwt 유효성 검사
     * 
     * @param   authToken   jwt 토큰
     * @return  유효한 값이면 True, 비유효값이면 False
     */
    public boolean validateJwtToken(String authToken) {
    	boolean validation = false;
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            validation = true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {유효하지 않은 토큰 서명} ", e.getMessage(), e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {유효하지 않은 토큰}", e.getMessage(), e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {토큰 시간 만료}", e.getMessage(), e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {지원하지 않은 토큰}", e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {ff}", e.getMessage(), e);
        }
        return validation;
    }

    /**
     * validateJwt 함수
     * Jwt 유효성 검사
     * 
     * @param   authToken   Front에서 넘겨준 jwt 값
     * @return  에러코드, 상태값 리턴
     */
    public Map<String, Object> validateJwt(String authToken) {
        Map<String, Object> jwtMap = new HashMap<String, Object>();
        boolean checkYN = false;

        try {
        	Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        	jwtMap.put("e0", "Success");
        	checkYN = true;
        } catch (SignatureException e) {
        	jwtMap.put("e1", "Invalid JWT signature -> Message: {유효하지 않은 토큰 서명}");
        } catch (MalformedJwtException e) {
        	jwtMap.put("e2", "Invalid JWT token -> Message: {유효하지 않은 토큰}");
        } catch (ExpiredJwtException e) {
        	jwtMap.put("e3", "Expired JWT token -> Message: {토큰 시간 만료}");
        } catch (UnsupportedJwtException e) {
        	jwtMap.put("e4", "Unsupported JWT token -> Message: {지원하지 않은 토큰}");
        } catch (IllegalArgumentException e) {
        	jwtMap.put("e5", "JWT claims string is empty -> Message: {ff}");
        }

        jwtMap.put("checkYN", checkYN);

        return jwtMap;
    }

    /**
     * getJwt 함수
     * request 헤더 값 중 Authorization 값을 가져와 "Bearer " 제거
     * (순수한 Jwt 값만 가져오기)
     * 
     * @return 	받으온 Bearer 토큰 값중 앞에  "Bearer " 제거
     */
    public String getHeaderJwt(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	authHeader = authHeader.replace("Bearer ", "");
        }

        return authHeader;
    }

    /**
     * getUserNameFromJwtToken 함수
     * Jwt 토큰 값 중 username(ID) 값 가져오기
     * 
     * @param   token   Jwt 토큰값
     * @return  Jwt 토큰 값 중 ID 값만 구분
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
        		.setSigningKey(jwtSecret)
        		.parseClaimsJws(token)
        		.getBody().getSubject();
    }

    /**
     * getUserKeyFromJwtToken 함수
     * Jwt 토큰 값 중 memberKey, AdminKey 값 가져오기
     * 
     * @param   token   Jwt 토큰값
     * @return  Jwt 토큰 값 중 Key 값만 구분
     */
    public Long getUserKeyFromJwtToken(String token) {
        return Long.valueOf(Jwts.parser()
        		.setSigningKey(jwtSecret)
        		.parseClaimsJws(token)
        		.getBody().getId());
    }

}
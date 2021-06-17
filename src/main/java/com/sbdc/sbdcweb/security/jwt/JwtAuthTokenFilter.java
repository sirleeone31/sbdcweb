package com.sbdc.sbdcweb.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbdc.sbdcweb.security.UserDetailsServiceImpl;

/**
 * JwtAuthTokenFilter
 * OncePerRequestFilter 상속 구현 클래스
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * doFilterInternal 함수
     * Jwt 토큰 가져와서 인증된 회원인지 확인 후 인증값, 권한 설정
     * 
     * @param request 		HttpServletRequest 객체
     * @param response 		HttpServletResponse 객체
     * @param filterChain 	FilterChain 객체
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
        	String jwt = getJwt(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set member authentication -> Message: {Jwt Token 인증 사용 불가}", e.getMessage(), e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * getJwt 함수
     * request 헤더 값 중 Authorization 값을 가져와 "Bearer " 제거
     * (순수한 Jwt 값만 가져오기)
     * 
     * @param 	request		HttpServletRequest 객체
     * @return 	받으온 Bearer 토큰 값중 앞에  "Bearer " 제거
     */
    private String getJwt(HttpServletRequest request) {
        String authHeader = null;

        authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	authHeader = authHeader.replace("Bearer ", "");
        }

        return authHeader;
    }

}
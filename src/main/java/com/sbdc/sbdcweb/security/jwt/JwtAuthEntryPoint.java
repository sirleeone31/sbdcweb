package com.sbdc.sbdcweb.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JwtAuthEntryPoint
 * AuthenticationEntryPoint 구현 클래스
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    /**
     * commence 함수
     * Jwt 인증 실패 핸들러
     * 
     * @param request 	request 객체
     * @param response 	response 객체
     * @param e 		AuthenticationException 객체
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        logger.error("Unauthorized error. Message - {인증 오류}", e.getMessage(), e);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
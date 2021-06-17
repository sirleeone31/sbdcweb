package com.sbdc.sbdcweb.mail.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.mail.service.EmailService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Email Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@RestController
@RequestMapping("/admin/mail")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class EmailRestController<T, ID> extends BaseRestController<T, ID> {
	protected EmailService<T, ID> emailService;
	protected AdminLogService adminLogService;
	protected CommonUtils commonUtils;
	protected JwtProvider jwtProvider;

    @Autowired
    public EmailRestController(EmailService<T, ID> emailService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(emailService);
    	this.emailService = emailService;
    	this.adminLogService = adminLogService;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * Email 전체목록 조회
     * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	code				emailCode 값
	 * @param 	emailListResponse	response 한 List 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    public ResponseEntity<List<T>> selectEmailList(HttpServletRequest request, String code, List<T> emailListResponse) {
		if (emailListResponse.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "목록 조회", "emailKey=" + emailListResponse.get(0).toString() + " 외 " + (emailListResponse.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(emailListResponse);
	}

    /**
	 * Email 특정 조회
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMAIL_KEY 값
	 * @param 	code			emailCode 값
	 * @param 	emailResponse	response 한 email 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> selectEmail(HttpServletRequest request, ID id, String code, T emailResponse) {
		if (emailResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "조회", "emailKey=" + emailResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(emailResponse);
	}

	/**
	 * Email 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @param 	code			emailCode 값
	 * @param 	emailResponse	response 한 email 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> insertEmail(HttpServletRequest request, T emailRequest, String code, T emailResponse) {
		if (emailResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "입력", "emailKey=" + emailResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Email 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMAIL_KEY 값
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @param 	code			emailCode 값
	 * @param 	emailResponse	response 한 email 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> updateEmail(HttpServletRequest request, ID id, T emailRequest, String code, T emailResponse) {
		if (emailResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "수정", "emailKey=" + emailResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * Email 삭제
	 * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	id					EMAIL_KEY 값
	 * @param 	code				emailCode 값
	 * @param 	emailMapResponse	response 한 email 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	public ResponseEntity<T> deleteEmail(HttpServletRequest request, ID id, String code, Map<String,Object> emailMapResponse) {
    	boolean deleteYN = (boolean) emailMapResponse.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

    	adminLogService.insertAdminLog(
    	        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
    			code, "삭제", "emailKey=" + emailMapResponse.get("emailKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
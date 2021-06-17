package com.sbdc.sbdcweb.mail.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.mail.domain.CivilEmail;
import com.sbdc.sbdcweb.mail.service.CivilEmailService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 고충처리 부정부패 알림메일발송관리 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Api(tags = {"2-1-12. 고충처리 부정부패 알림메일발송관리"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class CivilEmailRestController extends EmailRestController<CivilEmail, Long> {
	private final CivilEmailService civilEmailService;

    @Autowired
	public CivilEmailRestController(CivilEmailService civilEmailService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(civilEmailService, adminLogService, commonUtils, jwtProvider);
		this.civilEmailService = civilEmailService;
		this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

    /**
     * 고충처리 부정부패 알림메일발송관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/civil-email")
	public ResponseEntity<List<CivilEmail>> selectList(HttpServletRequest request) {
		return selectEmailList(request, civilEmailService.getEmailCode(), civilEmailService.selectList());
	}

	/**
	 * 고충처리 부정부패 알림메일발송관리 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/civil-email/{id}")
	public ResponseEntity<CivilEmail> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectEmail(request, id, civilEmailService.getEmailCode(), civilEmailService.select(id));
	}

    /**
	 * 고충처리 부정부패 알림메일발송관리 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/civil-email")
	public ResponseEntity<CivilEmail> insert(HttpServletRequest request, @RequestBody CivilEmail emailRequest) {
    	return super.insertEmail(request, emailRequest, civilEmailService.getEmailCode(), civilEmailService.insert(emailRequest));
	}

	/**
	 * 고충처리 부정부패 알림메일발송관리 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMAIL_KEY 값
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/civil-email/{id}")
	public ResponseEntity<CivilEmail> update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody CivilEmail emailRequest) {
		return super.updateEmail(request, id, emailRequest, civilEmailService.getEmailCode(), civilEmailService.update(id, emailRequest));
	}

	/**
	 * 고충처리 부정부패 알림메일발송관리 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/civil-email/{id}")
	public ResponseEntity<CivilEmail> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteEmail(request, id, civilEmailService.getEmailCode(), civilEmailService.delete(id));
	}

}
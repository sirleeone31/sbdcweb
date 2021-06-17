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
import com.sbdc.sbdcweb.mail.domain.EqnaEmail;
import com.sbdc.sbdcweb.mail.service.EqnaEmailService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 윤리경영관리 알림메일발송관리 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Api(tags = {"1-2-5. 윤리경영관리 알림메일발송관리"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class EqnaEmailRestController extends EmailRestController<EqnaEmail, Long> {
	private final EqnaEmailService eqnaEmailService;

    @Autowired
	public EqnaEmailRestController(EqnaEmailService eqnaEmailService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(eqnaEmailService, adminLogService, commonUtils, jwtProvider);
		this.eqnaEmailService = eqnaEmailService;
		this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
    }

    /**
     * 윤리경영관리 알림메일발송관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/eqna-email")
	public ResponseEntity<List<EqnaEmail>> selectList(HttpServletRequest request) {
		return selectEmailList(request, eqnaEmailService.getEmailCode(), eqnaEmailService.selectList());
	}

	/**
	 * 윤리경영관리 알림메일발송관리 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/eqna-email/{id}")
	public ResponseEntity<EqnaEmail> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectEmail(request, id, eqnaEmailService.getEmailCode(), eqnaEmailService.select(id));
	}

    /**
	 * 윤리경영관리 알림메일발송관리 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/eqna-email")
	public ResponseEntity<EqnaEmail> insert(HttpServletRequest request, @RequestBody EqnaEmail emailRequest) {
    	return super.insertEmail(request, emailRequest, eqnaEmailService.getEmailCode(), eqnaEmailService.insert(emailRequest));
	}

	/**
	 * 윤리경영관리 알림메일발송관리 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMAIL_KEY 값
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/eqna-email/{id}")
	public ResponseEntity<EqnaEmail> update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody EqnaEmail emailRequest) {
		return super.updateEmail(request, id, emailRequest, eqnaEmailService.getEmailCode(), eqnaEmailService.update(id, emailRequest));
	}

	/**
	 * 윤리경영관리 알림메일발송관리 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/eqna-email/{id}")
	public ResponseEntity<EqnaEmail> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteEmail(request, id, eqnaEmailService.getEmailCode(), eqnaEmailService.delete(id));
	}

}
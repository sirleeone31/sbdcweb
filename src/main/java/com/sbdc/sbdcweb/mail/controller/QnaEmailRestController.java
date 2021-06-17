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
import com.sbdc.sbdcweb.mail.domain.QnaEmail;
import com.sbdc.sbdcweb.mail.service.QnaEmailService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 고객상담관리 알림메일발송관리 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Api(tags = {"1-2-4. 고객상담관리 알림메일발송관리"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class QnaEmailRestController extends EmailRestController<QnaEmail, Long> {
	private final QnaEmailService qnaEmailService;

    @Autowired
	public QnaEmailRestController(QnaEmailService qnaEmailService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(qnaEmailService, adminLogService, commonUtils, jwtProvider);
		this.qnaEmailService = qnaEmailService;
		this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
    }

    /**
     * 고객상담관리 알림메일발송관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/qna-email")
	public ResponseEntity<List<QnaEmail>> selectList(HttpServletRequest request) {
		return selectEmailList(request, qnaEmailService.getEmailCode(), qnaEmailService.selectList());
	}

	/**
	 * 고객상담관리 알림메일발송관리 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/qna-email/{id}")
	public ResponseEntity<QnaEmail> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectEmail(request, id, qnaEmailService.getEmailCode(), qnaEmailService.select(id));
	}


    /**
	 * 고객상담관리 알림메일발송관리 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/qna-email")
	public ResponseEntity<QnaEmail> insert(HttpServletRequest request, @RequestBody QnaEmail emailRequest) {
    	return super.insertEmail(request, emailRequest, qnaEmailService.getEmailCode(), qnaEmailService.insert(emailRequest));
	}

	/**
	 * 고객상담관리 알림메일발송관리 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMAIL_KEY 값
	 * @param 	emailRequest	Front에서 입력된 email 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/qna-email/{id}")
	public ResponseEntity<QnaEmail> update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody QnaEmail emailRequest) {
		return super.updateEmail(request, id, emailRequest, qnaEmailService.getEmailCode(), qnaEmailService.update(id, emailRequest));
	}

	/**
	 * 고객상담관리 알림메일발송관리 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMAIL_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/qna-email/{id}")
	public ResponseEntity<QnaEmail> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteEmail(request, id, qnaEmailService.getEmailCode(), qnaEmailService.delete(id));
	}

}
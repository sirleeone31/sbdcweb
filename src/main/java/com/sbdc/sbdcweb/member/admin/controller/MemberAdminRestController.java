package com.sbdc.sbdcweb.member.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.mail.service.MailingService;
import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.member.domain.MemberDto;
import com.sbdc.sbdcweb.member.service.MemberService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 회원 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}", maxAge = 3600)
@RestController
@RequestMapping("/admin/api/auth")
public class MemberAdminRestController extends BaseRestController<Member, Long> {
	private final MemberService memberService;
	private final AdminLogService adminLogService;
	private final MailingService mailService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public MemberAdminRestController(MemberService memberService, AdminLogService adminLogService, MailingService mailService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(memberService);
    	this.memberService = memberService;
    	this.adminLogService = adminLogService;
    	this.mailService = mailService;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 회원 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/member")
	public ResponseEntity<List<MemberDto>> selectMemberList(HttpServletRequest request) {
		List<MemberDto> memberList = memberService.selectMemberList();

		if (memberList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				memberService.getCode(), "목록 조회", "[접속기록] memberKey=" + memberList.get(0).getMemberKey() + " 외 " + (memberList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(memberList);
	}

	/**
	 * 회원 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MEMBER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/member/{id}")
	public ResponseEntity<MemberDto> selectMember(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		MemberDto member = memberService.selectMember(id);

		if (member == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				memberService.getCode(), "조회", "[접속기록] memberKey=" + member.getMemberKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(member);
	}

    /**
	 * 회원 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				MEMBER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/member/{id}")
	public ResponseEntity<Member> updateMember(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Member member = memberService.updateMember(id);

		if (member == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				memberService.getCode(), "수정", "[접속기록] memberKey=" + member.getMemberKey(), commonUtils.findIp(request));

		mailService.sendMail(member.getEmail(), member.getPassword());

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 회원 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MEMBER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/member/{id}")
	public ResponseEntity<Member> deleteMember(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Map<String, Object> memberMap = memberService.deleteMember(id);
    	boolean deleteYN = (boolean) memberMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				memberService.getCode(), "삭제", "[접속기록] memberKey=" + memberMap.get("memberKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
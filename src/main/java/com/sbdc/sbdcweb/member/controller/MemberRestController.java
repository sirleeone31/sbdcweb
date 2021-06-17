package com.sbdc.sbdcweb.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.NiceIDUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.member.domain.MemberDto;
import com.sbdc.sbdcweb.member.domain.request.ChangeForm;
import com.sbdc.sbdcweb.member.domain.request.ChangePwForm;
import com.sbdc.sbdcweb.member.domain.request.DeleteForm;
import com.sbdc.sbdcweb.member.domain.request.FindForm;
import com.sbdc.sbdcweb.member.domain.request.LoginForm;
import com.sbdc.sbdcweb.member.domain.request.NiceForm;
import com.sbdc.sbdcweb.member.domain.request.SignUpForm;
import com.sbdc.sbdcweb.member.domain.response.JwtResponse;
import com.sbdc.sbdcweb.member.domain.response.ResponseMessage;
import com.sbdc.sbdcweb.member.service.CertificationService;
import com.sbdc.sbdcweb.member.service.MemberService;
import com.sbdc.sbdcweb.member.service.RetireService;

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
@RequestMapping("/api/auth")
public class MemberRestController extends BaseRestController<Member, Long> {
	private final MemberService memberService;
	private final RetireService retireService;
	private final CertificationService certificationService;
	private final CommonUtils commonUtils;
    private final NiceIDUtils niceIDUtils;

	@Autowired
    public MemberRestController(MemberService memberService, 
    		RetireService retireService,
    		CertificationService certificationService,
    		CommonUtils commonUtils, 
    		NiceIDUtils niceIDUtils) {
    	super(memberService);
    	this.memberService = memberService;
    	this.retireService = retireService;
    	this.certificationService = certificationService;
    	this.commonUtils = commonUtils;
    	this.niceIDUtils = niceIDUtils;
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

		return ResponseEntity.status(HttpStatus.OK).body(member);
	}

    /**
	 * 회원 수정(마이페이지)
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				MEMBER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/member/{id}")
	public ResponseEntity<Member> updateMember(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody ChangeForm changeRequest) {
		Member member = memberService.updateMember(id, changeRequest);

		if (member == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 회원 삭제(탈퇴)
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				MEMBER_KEY 값
	 * @param 	deleteRequest	Front에서 요청 받은 deleteForm
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/member/{id}")
	public ResponseEntity<Member> deleteMember(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody DeleteForm deleteRequest) {
		Map<String, Object> memberMap = memberService.deleteMember(id, deleteRequest);
    	boolean deleteYN = (boolean) memberMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

    	Long memberType = (Long) memberMap.get("memberType");
    	String companyName = (String) memberMap.get("companyName");
    	Long memberKey = (Long) memberMap.get("memberKey");
    	String retireComment = deleteRequest.getRetireComment();
    	String ip = commonUtils.findIp(request);

    	// LOG 처리
    	retireService.insertRetireLog(memberType, companyName, memberKey, retireComment, ip);

    	return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * Nice 인증 모듈 불러오기
     *
     * @param 	request  HttpServletRequest 객체(인증모듈 중 session 사용)
     * @param 	flag     회원가입(join), 아이디찾기(findid), 비번찾기(findpw) 구분자
	 * @return	ResponseEntity(Http 상태값, body 값, 인증결과값)
     */
//    @GetMapping("/certification/{flag}")
    @RequestMapping(value="/certification/{flag}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<NiceForm> certiPhone(HttpServletRequest request, @PathVariable("flag") String flag) {
        Map<String, Object> certiMap = niceIDUtils.NiceID(request, flag);

        String sMessage = (String) certiMap.get("sMessage");
        String sEncData = (String) certiMap.get("sEncData");
        int iReturn = (int) certiMap.get("iReturn");

        NiceForm niceForm = new NiceForm(sMessage, sEncData, iReturn);

		return ResponseEntity.status(HttpStatus.OK).body(niceForm);
    }

    /**
	 * 회원가입
	 * 
     * @param 	request  		HttpServletRequest 객체
	 * @param 	signUpRequest	Front에서 요청 받은 SignUpForm
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/signup")
    public ResponseEntity<?> signUpMember(HttpServletRequest request, @RequestBody SignUpForm signUpRequest) {
    	// 본인 확인 우회 가능 조치
    	if (!signUpRequest.getName().equals(certificationService.selectCertifi(signUpRequest.getCertCi()))) {
    		certificationService.deleteCertifi(signUpRequest.getCertCi());
    		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    	}

    	Member member = memberService.signUpMember(signUpRequest, commonUtils.findIp(request));

		if (member == null) {
			certificationService.deleteCertifi(signUpRequest.getCertCi());
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		certificationService.deleteCertifi(signUpRequest.getCertCi());

		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
	 * 로그인
	 * 
     * @param 	request  		HttpServletRequest 객체
	 * @param 	loginRequest	Front에서 요청 받은 LoginForm
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/signin")
    public ResponseEntity<?> signInMember(HttpServletRequest request, @RequestBody LoginForm loginRequest) {
        Map<String, Object> memberMap = memberService.signInMember(loginRequest);
        boolean signInYN = (boolean) memberMap.get("signInYN");

    	if (!signInYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

    	String jwt = (String) memberMap.get("jwt");
        UserDetails userDetails = (UserDetails) memberMap.get("userDetails");

        // LOG 처리
        memberService.updateMemberLog(userDetails.getUsername(), commonUtils.findIp(request));
        
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    /**
	 * 비번찾기 시 아이디 확인
	 * 
	 * @param 	findRequest		Front에서 요청 받은 FindForm
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/member/pw/id")
    public ResponseEntity<?> existsMemberIdPw(@RequestBody FindForm findRequest) {
        boolean exists = memberService.existsMemberId(findRequest.getUsername());

        if (!exists) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 비번변경
     *
	 * @param 	changeRequest	Front에서 요청 받은 ChangePwForm
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @PatchMapping("/member/pw")
    public ResponseEntity<?> updateMemberPassword(@RequestBody ChangePwForm changeRequest) {
    	Map<String, Object> memberMap = memberService.updateMemberPassword(changeRequest.getPassword(), changeRequest.getCertCi());

        boolean changeYN = (boolean) memberMap.get("changeYN");

        if (!changeYN) {
        	return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseMessage(changeYN));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(changeYN));
    }

    /**
     * 사업자번호 중복체크 및 유효성 체크
     *
	 * @param 	findRequest		Front에서 요청 받은 FindForm
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @PostMapping("/member/regno")
    public ResponseEntity<?> validationRegNo(@RequestBody FindForm findRequest) {
//        boolean exists = memberService.existsMemberId(findRequest.getUsername());
    	boolean validYN = memberService.validationRegNo(findRequest.getRegNo());

        if (!validYN) {
//            if (exists == true || regNoValid == false) {
        	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(validYN));
        }

    	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(validYN));
    }

    /**
	 * 회원가입 시 아이디 확인
	 * 
	 * @param 	findRequest		Front에서 요청 받은 FindForm
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/member/id")
    public ResponseEntity<?> existsMemberId(@RequestBody FindForm findRequest) {
        boolean exists = memberService.existsMemberId(findRequest.getUsername());

        if (exists) {
        	// 이미 가입된 ID 있을 경우 response(false)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(false));
        }
    	// 가입된 ID 없을 경우 response(true)
    	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(true));
    }

}
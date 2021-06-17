package com.sbdc.sbdcweb.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sbdc.sbdcweb.common.NiceIDUtils;
import com.sbdc.sbdcweb.config.ApplicationProperties;
import com.sbdc.sbdcweb.member.domain.response.NiceResponse;
import com.sbdc.sbdcweb.member.domain.response.ResponseMessage;
import com.sbdc.sbdcweb.member.service.CertificationService;
import com.sbdc.sbdcweb.member.service.MemberService;

/**
 * 휴대폰인증 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-20
 */
@RestController
@RequestMapping("/nice")
public class NiceRestController {
    private final MemberService memberService;
    private final CertificationService certificationService;
    private final NiceIDUtils niceIDUtils;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public NiceRestController(MemberService memberService, 
    		CertificationService certificationService, 
    		NiceIDUtils niceIDUtils, 
    		ApplicationProperties applicationProperties) {
        this.memberService = memberService;
        this.certificationService = certificationService;
        this.niceIDUtils = niceIDUtils;
        this.applicationProperties = applicationProperties;
    }

    /**
     * 휴대폰 인증 성공
     * 
	 * @param 	request		HttpServletRequest 객체
     * @param 	flag     	회원가입(join), 아이디찾기(findid), 비번찾기(findpw) 구분자
	 * @return	ModelAndView(성공 시 flag 별 response 값을 각각 html 로 redirect)
     */
//    @PostMapping("/certificationSuccess/{flag}")
    @RequestMapping(value="/certificationSuccess/{flag}", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView certificationSuccess(HttpServletRequest request, @PathVariable("flag") String flag) {
        Map<String, Object> certiMap = niceIDUtils.NiceIDSuccess(request);

        String sMessage = (String) certiMap.get("sMessage");
        String sCipherTime = (String) certiMap.get("sCipherTime");
        String sRequestNumber = (String) certiMap.get("sRequestNumber");
        String sResponseNumber = (String) certiMap.get("sResponseNumber");
        String sAuthType = (String) certiMap.get("sAuthType");
        String sName = (String) certiMap.get("sName");
        String sDupInfo = (String) certiMap.get("sDupInfo");
        String sConnInfo = (String) certiMap.get("sConnInfo");
        String sBirthDate = (String) certiMap.get("sBirthDate");
        String sGender = (String) certiMap.get("sGender");
        String sNationalInfo = (String) certiMap.get("sNationalInfo");
        String sMobileNo = (String) certiMap.get("sMobileNo");
        String sMobileCo = (String) certiMap.get("sMobileCo");
        int iReturn = (int) certiMap.get("iReturn");

        // 회원가입, ID찾기, PW찾기 분리
        if (flag.equals("join")) {
        	// 중복가입
        	if (memberService.existsDI(sDupInfo) == true) {
        		iReturn = 1;
        	}
        	// 14세 미만
        	if (memberService.adultCheck(sBirthDate) == false) {
        		iReturn = 2;
        	}
        } else if (flag.equals("findid")) {
        	// CI 이용하여 memberId 조회
        	String memberId = memberService.selectMemberId(sConnInfo);

        	// Id 마스킹 처리
//          memberId = commonUtils.stringMasking(memberId);
        	certiMap.put("memberId", memberId);
        } else if (flag.equals("findpw")) {
        	// findpw 처리 있을 경우 추가
        }

        NiceResponse niceResponse = new NiceResponse(sName, sDupInfo, sConnInfo, sMobileNo, iReturn);

//        session 사용하여 핸들러쪽으로 값 넘겨주기
//        HttpSession session = request.getSession();
//        session.setAttribute("sName", niceResponse.getSName());
//        session.setAttribute("sDupInfo", niceResponse.getSDupInfo());
//        session.setAttribute("sConnInfo", niceResponse.getSConnInfo());
//        session.setAttribute("sMobileNo", niceResponse.getSMobileNo());
//        session.setAttribute("iReturn", niceResponse.getIReturn());

        ModelAndView mav = new ModelAndView();

        mav.addObject("flag", flag);
        mav.addObject("sName", niceResponse.getSName());
        mav.addObject("sMobileNo", niceResponse.getSMobileNo());
        mav.addObject("iReturn", niceResponse.getIReturn());

        if (flag.equals("join")) {
            mav.addObject("sDupInfo", niceResponse.getSDupInfo());
            mav.addObject("sConnInfo", niceResponse.getSConnInfo());

            mav.setViewName("redirect:" + applicationProperties.getFrontServerAPI() + "/assets/join.html");

            certificationService.insertCertifi(niceResponse.getSName(), niceResponse.getSDupInfo(), niceResponse.getSConnInfo(), niceResponse.getSMobileNo());
        } else if (flag.equals("findid")) {
            String memberId = (String) certiMap.get("memberId");
            mav.addObject("memberId", memberId);

            mav.setViewName("redirect:" + applicationProperties.getFrontServerAPI() + "/assets/findid.html");
        } else if (flag.equals("findpw")) {
            mav.addObject("sDupInfo", niceResponse.getSDupInfo());
            mav.addObject("sConnInfo", niceResponse.getSConnInfo());

            mav.setViewName("redirect:" + applicationProperties.getFrontServerAPI() + "/assets/findpw.html");
        }

        return mav;
    }

/*
    // session 서버 처리 시 핸들러(GetMapping으로 핸들러를 통한 인증값 가져오기)
    @GetMapping("/certificationSuccessHandler")
    public ResponseEntity<NiceResponse> certificationCellphoneSuccessHandler(HttpServletRequest request) {
        NiceResponse niceResponse = new NiceResponse();
        HttpSession session = request.getSession();

        String sName = (String) session.getAttribute("sName");
        String sDupInfo = (String) session.getAttribute("sDupInfo");
        String sConnInfo = (String) session.getAttribute("sConnInfo");
        String sMobileNo = (String) session.getAttribute("sMobileNo");
        int iReturn = (int) session.getAttribute("iReturn");

        niceResponse.setSName(sName);
        niceResponse.setSDupInfo(sDupInfo);
        niceResponse.setSConnInfo(sConnInfo);
        niceResponse.setSMobileNo(sMobileNo);
        niceResponse.setIReturn(iReturn);

        return ResponseEntity.ok().body(niceResponse);
    }
*/

/*
    // PostMapping을 GetMapping 핸들러로 객체 값 포함하여 전송 
    @PostMapping("/certificationSuccess")
    public ResponseEntity<String> certificationCellphoneSuccess(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        HttpHeaders headers = new HttpHeaders();
        NiceResponse niceResponse = new NiceResponse();

        Map<String, Object> certiMap = memberService.certiCellphoneSuccess(request, niceResponse);
        niceResponse = (NiceResponse) certiMap.get("niceResponse");

        String iReturn = String.valueOf(niceResponse.getIReturn());
        headers.add("iReturn", iReturn);
        headers.add("sConnInfo", niceResponse.getSConnInfo());
        headers.add("sDupInfo", niceResponse.getSDupInfo());

        redirectAttrs.addFlashAttribute("niceResponse", niceResponse);
        headers.setLocation(URI.create("certificationSuccessHandler"));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // 핸들러로 전송한 객체 값 처리 
    @GetMapping("/certificationSuccessHandler")
    public ResponseEntity<NiceResponse> certificationCellphoneSuccessHandler(HttpServletRequest request) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        NiceResponse niceResponse = new NiceResponse();

        if (null != inputFlashMap) {
            niceResponse = (NiceResponse) inputFlashMap.get("niceResponse");
        }

        return ResponseEntity.ok().body(niceResponse);
    }
*/

    /**
     * 휴대폰 인증 실패
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
//    @PostMapping("/certificationFail")
    @RequestMapping(value="/certificationFail", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> certificationFail(HttpServletRequest request) {
        Map<String, Object> certiMap = niceIDUtils.NiceIDFail(request);

        String sCipherTime = (String) certiMap.get("sCipherTime");
        String sRequestNumber = (String) certiMap.get("sRequestNumber");
        String sErrorCode = (String) certiMap.get("sErrorCode");
        String sAuthType = (String) certiMap.get("sAuthType");
        String sMessage = (String) certiMap.get("sMessage");
        int iReturn = (int) certiMap.get("iReturn");

        NiceResponse niceResponse = new NiceResponse(iReturn);
//        niceResponse.getIReturn();

//        return ResponseEntity.badRequest().body(niceResponse);
//        return new ResponseEntity<>(new ResponseMessage("Certification Fail"), HttpStatus.BAD_REQUEST);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Certification Fail", iReturn));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Certification Fail"));
    }

}
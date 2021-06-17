package com.sbdc.sbdcweb.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.config.ApplicationProperties;

import NiceID.Check.CPClient;

/**
 * 공통 관리
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-04-25
 *
 */
@Service("niceIDUtils")
public class NiceIDUtils {
	private static final Logger logger = LoggerFactory.getLogger(NiceIDUtils.class);

	private final ApplicationProperties applicationProperties;

	/**
     * Service 생성자 주입
     */
    @Autowired
    public NiceIDUtils(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Nice 인증 모듈 불러오기
     * @param   request     HttpServletRequest 객체(인증모듈 중 session 사용)
     *
     * @return  niceIDMap   유효한 인증 절차 검증 후 sEncData 값 리턴
     */
    public Map<String, Object> NiceID(HttpServletRequest request, String flag) {
        HttpSession session = request.getSession();

        Map<String, Object> niceIDMap = new HashMap<String, Object>();

        CPClient niceCheck = new CPClient();

        String sSiteCode = "";                 // NICE로부터 부여받은 사이트 코드
        String sSitePassword = "";      // NICE로부터 부여받은 사이트 패스워드

        String sRequestNumber = "REQ0000000001";    // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.

        sRequestNumber = niceCheck.getRequestNO(sSiteCode);

        session.setAttribute("REQ_SEQ" , sRequestNumber);   // 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

        String sAuthType = "";          // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서

        String popgubun     = "N";      //Y : 취소버튼 있음 / N : 취소버튼 없음
        String customize    = "";       //없으면 기본 웹페이지 / Mobile : 모바일페이지

        String sGender = "";            //없으면 기본 선택 값, 0 : 여자, 1 : 남자

        // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        //리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
//        String sReturnUrl = "http://localhost:8081/nice/certificationSuccess";      // 성공시 이동될 URL
//        String sErrorUrl = "http://localhost:8081/nice/certificationFail";          // 실패시 이동될 URL
        String sReturnUrl = applicationProperties.getFrontServerAPI() + "/nice/certificationSuccess";      // 성공시 이동될 URL
        String sErrorUrl = applicationProperties.getFrontServerAPI() + "/nice/certificationFail";          // 실패시 이동될 URL

        // 회원가입, ID찾기, PW찾기 분리
        if (flag.equals("join")) {
            sReturnUrl = sReturnUrl + "/join";
        } else if (flag.equals("findid")) {
            sReturnUrl = sReturnUrl + "/findid";
        } else if (flag.equals("findpw")) {
            sReturnUrl = sReturnUrl + "/findpw";
        }

        // 입력될 plain 데이타를 만든다.
        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
                "6:GENDER" + sGender.getBytes().length + ":" + sGender;

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

        if( iReturn == 0 )
        {
            sEncData = niceCheck.getCipherData();
        }
        else if( iReturn == -1)
        {
            sMessage = "암호화 시스템 에러입니다.";
        }
        else if( iReturn == -2)
        {
            sMessage = "암호화 처리오류입니다.";
        }
        else if( iReturn == -3)
        {
            sMessage = "암호화 데이터 오류입니다.";
        }
        else if( iReturn == -9)
        {
            sMessage = "입력 데이터 오류입니다.";
        }
        else
        {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        niceIDMap.put("sMessage", sMessage);
        niceIDMap.put("sEncData", sEncData);
        niceIDMap.put("iReturn", iReturn);

        return niceIDMap;

    }

    /**
     * Nice 인증 성공시
     * @param   request     HttpServletRequest 객체(인증모듈 중 session 사용)
     *
     * @return  niceIDMap   인증 성공 후 DI, CI 값 리턴
     */
    @SuppressWarnings("rawtypes")
    public Map<String, Object> NiceIDSuccess(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, Object> niceIDMap = new HashMap<String, Object>();

        CPClient niceCheck = new CPClient();

        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

        String sSiteCode = "";             // NICE로부터 부여받은 사이트 코드
        String sSitePassword = "";  // NICE로부터 부여받은 사이트 패스워드

        String sCipherTime = "";            // 복호화한 시간
        String sRequestNumber = "";         // 요청 번호
        String sResponseNumber = "";        // 인증 고유번호
        String sAuthType = "";              // 인증 수단
        String sName = "";                  // 성명
        String sDupInfo = "";               // 중복가입 확인값 (DI_64 byte)
        String sConnInfo = "";              // 연계정보 확인값 (CI_88 byte)
        String sBirthDate = "";             // 생년월일(YYYYMMDD)
        String sGender = "";                // 성별
        String sNationalInfo = "";          // 내/외국인정보 (개발가이드 참조)
        String sMobileNo = "";              // 휴대폰번호
        String sMobileCo = "";              // 통신사
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 )
        {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            HashMap mapresult = niceCheck.fnParse(sPlainData);

            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
            sResponseNumber = (String)mapresult.get("RES_SEQ");
            sAuthType       = (String)mapresult.get("AUTH_TYPE");
//          sName           = (String)mapresult.get("NAME");
            sName           = (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
            sBirthDate      = (String)mapresult.get("BIRTHDATE");
            sGender         = (String)mapresult.get("GENDER");
            sNationalInfo   = (String)mapresult.get("NATIONALINFO");
            sDupInfo        = (String)mapresult.get("DI");
            sConnInfo       = (String)mapresult.get("CI");
            sMobileNo       = (String)mapresult.get("MOBILE_NO");
            sMobileCo       = (String)mapresult.get("MOBILE_CO");

            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
            if(!sRequestNumber.equals(session_sRequestNumber))
            {
                sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
                sResponseNumber = "";
                sAuthType = "";
            }
        }
        else if( iReturn == -1)
        {
            sMessage = "복호화 시스템 에러입니다.";
        }
        else if( iReturn == -4)
        {
            sMessage = "복호화 처리오류입니다.";
        }
        else if( iReturn == -5)
        {
            sMessage = "복호화 해쉬 오류입니다.";
        }
        else if( iReturn == -6)
        {
            sMessage = "복호화 데이터 오류입니다.";
        }
        else if( iReturn == -9)
        {
            sMessage = "입력 데이터 오류입니다.";
        }
        else if( iReturn == -12)
        {
            sMessage = "사이트 패스워드 오류입니다.";
        }
        else
        {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        // Decode 처리
        try {
	        sName = URLDecoder.decode(sName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        	logger.error("decodeError", e.getMessage(), e);
		}

        niceIDMap.put("sMessage", sMessage);
        niceIDMap.put("sCipherTime", sCipherTime);
        niceIDMap.put("sRequestNumber", sRequestNumber);
        niceIDMap.put("sResponseNumber", sResponseNumber);
        niceIDMap.put("sAuthType", sAuthType);
        niceIDMap.put("sName", sName);
        niceIDMap.put("sDupInfo", sDupInfo);
        niceIDMap.put("sConnInfo", sConnInfo);
        niceIDMap.put("sBirthDate", sBirthDate);
        niceIDMap.put("sGender", sGender);
        niceIDMap.put("sNationalInfo", sNationalInfo);
        niceIDMap.put("sMobileNo", sMobileNo);
        niceIDMap.put("sMobileCo", sMobileCo);
        niceIDMap.put("iReturn", iReturn);

        return niceIDMap;

    }

    /**
     * Nice 인증 실패시
     * @param   request     HttpServletRequest 객체
     *
     * @return  niceIDMap   인증 실패 시 iReturn 값 리턴
     */
    @SuppressWarnings("rawtypes")
    public Map<String, Object> NiceIDFail(HttpServletRequest request) {
        Map<String, Object> niceIDMap = new HashMap<String, Object>();

        CPClient niceCheck = new CPClient();
        
        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

        String sSiteCode = "";                 // NICE로부터 부여받은 사이트 코드
        String sSitePassword = "";      // NICE로부터 부여받은 사이트 패스워드

        String sCipherTime = "";                    // 복호화한 시간
        String sRequestNumber = "";                 // 요청 번호
        String sErrorCode = "";                     // 인증 결과코드
        String sAuthType = "";                      // 인증 수단
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 )
        {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            HashMap mapresult = niceCheck.fnParse(sPlainData);
            
            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
            sErrorCode      = (String)mapresult.get("ERR_CODE");
            sAuthType       = (String)mapresult.get("AUTH_TYPE");
        }
        else if( iReturn == -1)
        {
            sMessage = "복호화 시스템 에러입니다.";
        }    
        else if( iReturn == -4)
        {
            sMessage = "복호화 처리오류입니다.";
        }    
        else if( iReturn == -5)
        {
            sMessage = "복호화 해쉬 오류입니다.";
        }    
        else if( iReturn == -6)
        {
            sMessage = "복호화 데이터 오류입니다.";
        }    
        else if( iReturn == -9)
        {
            sMessage = "입력 데이터 오류입니다.";
        }    
        else if( iReturn == -12)
        {
            sMessage = "사이트 패스워드 오류입니다.";
        }    
        else
        {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        niceIDMap.put("sCipherTime", sCipherTime);
        niceIDMap.put("sRequestNumber", sRequestNumber);
        niceIDMap.put("sErrorCode", sErrorCode);
        niceIDMap.put("sAuthType", sAuthType);
        niceIDMap.put("sMessage", sMessage);
        niceIDMap.put("iReturn", iReturn);

        return niceIDMap;

    }

    /**
     * encodeData 값 Replace
     * @param   paramValue      encodeData 값
     * @param   gubun           encodeData 구분 값
     *
     * @return  result          replace 작업 후 값
     *
     */
    public String requestReplace (String paramValue, String gubun) {

        String result = "";

        if (paramValue != null) {

            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if(gubun != "encodeData"){
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }

            result = paramValue;

        }
        return result;
    }

}
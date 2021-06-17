package com.sbdc.sbdcweb.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 공통 관리
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Service("commonUtils")
public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 서버에 접속한 사용자 IP 찾기
     * 
     * @param request	접속 IP 확인을 위한 request 변수
     * @return 			사용자 접속 IP 반환
     */
    public String findIp(HttpServletRequest request) {
    	String clientIp = request.getHeader("X-Forwarded-For");

    	if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }

    	if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    /**
     * Number 체크
     * 
     * @param strNum    String 값이 숫자 형태인지 체크
     * @return          숫자형태이면 true, 아니면 false
     */
    public Boolean isNumber(String strNum) {
    	boolean isNumber = false;

        try {
            Long.parseLong(strNum);
//            Double.parseDouble(strNum);
            isNumber = true;
        } catch(NumberFormatException e) {
			logger.error("Number 체크 에러", e.getMessage(), e);
        }

        return isNumber;
    }

    /**
     * 사업자 번호 체크 xxx-xx-xxxxx 형식의 사업자번호 앞,중간,뒤 문자열 3개 입력 받아 유효한 사업자번호인지 검사.
     * 
     * @param   compNumber  검증할 사업자번호
     * @return              유효한 사업자번호인지 여부 (True/False)
     */
    public Boolean checkCompNumber(String compNumber) {
    	boolean checkYN = false;
    	int hap = 0;
        int temp = 0;

        //사업자번호 유효성 체크 필요한 수
        int check[] = {1,3,7,1,3,7,1,3,5};

        try {
            //사업자번호의 길이가 맞는지를 확인한다.
            if(compNumber.length() != 10) {
        		throw new Exception();
            }

            for(int i=0; i < 9; i++){
                //숫자가 아닌 값이 들어왔는지를 확인한다.
                if(compNumber.charAt(i) < '0' || compNumber.charAt(i) > '9') {
            		throw new Exception();
                }

                //검증식 적용
                hap = hap + (Character.getNumericValue(compNumber.charAt(i)) * check[temp]);
                temp++;
            }

            hap += (Character.getNumericValue(compNumber.charAt(8))*5)/10;

            //마지막 유효숫자와 검증식을 통한 값의 비교
            if ((10 - (hap%10))%10 == Character.getNumericValue(compNumber.charAt(9))) {
                return true;
            } else {
        		throw new Exception();
            }
        } catch(Exception e) {
			logger.error("사업자 번호 체크 에러", e.getMessage(), e);
        }

        return checkYN;
    }

    /**
     * 마스킹 처리(2자리부터 마스킹)
     * 
     * @param   maskingWord     마스킹 처리할 String
     * @return                  마스킹 처리된 String
     */
    public String stringMasking(String maskingWord) {
        return maskingWord.replaceAll("(?<=.{1})." , "*");
    }

    /**
     * Front Date 값의 현지 시각 적용 및 DB Date Format 적용
     *
     * @param 	beforeDate format 적용 전 Date
     * @return 	afterDate format 적용 후 Date
     */
    public String frontEndDateFormatter(String beforeDate) {
        Date date = null;
        String afterDate = null;

		SimpleDateFormat backEndDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat frontEndDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.US);
        // Front Date 입력 값 현재 시간 맞춰주기
		frontEndDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

//      SimpleDateFormat frontFormatter = new SimpleDateFormat("yyyy. M. dd.");
//      frontFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			date = frontEndDateFormat.parse(beforeDate);
			afterDate = backEndDateFormat.format(date);
		} catch (ParseException e) {
			logger.error("Front Date 값의 현지 시각 적용 및 DB Date Format 적용 에러", e.getMessage(), e);
		}

        return afterDate;
    }

    /**
     * 현재 시각 DB Date Format 적용
     *
     * @return 	nowDate format 적용 후 Date
     */
    public String nowDateFormatter() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String nowDate = dateFormat.format(System.currentTimeMillis());
        return nowDate;
    }

    /**
     * 등록일 차이 계산
     *
     * @param   maxRegDate	memberKey 등록일
     * @return 	diffDate
     */
    public Long diffRegDate(String maxRegDate) {
        long nowDate = 0;
        long maxDate = 0;
        long diffDate = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
			nowDate = System.currentTimeMillis();
			maxDate = dateFormat.parse(maxRegDate).getTime();
			diffDate = (nowDate - maxDate) / 1000;
		} catch (ParseException e) {
            logger.error("등록일 차이 계산 에러", e.getMessage(), e);
		}

        return diffDate;
    }

    /**
     * 등록일 차이 TimeOut 기본값 설정
     *
     * @return 	regDateTimeOut
     */
    public Long regDateTimeOut() {
    	Long regDateTimeOut = 60L;
        return regDateTimeOut;
    }

    /**
     * 난수 암호 생성
     *
     * @return 	newPassword UUID를 활용한 난수 암호(- 제거 후 8자리까지)
     */
    public String randomPassword() {
    	String newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        return newPassword;
    }

    /**
     * 14세 미만 확인
     * 
     * @param   sBirthDate	Nice 휴대폰 인증을 통해서 넘어온 생년월일 값
     * @return  14세 이상인 경우 true, 미만인 경우 false
     */
    public Boolean adultCheck(String sBirthDate) {
    	boolean adultYN = false;

    	Date currentDate = new Date();
    	DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 

        int birthDt = Integer.parseInt(sBirthDate);                            
        int currentDt = Integer.parseInt(formatter.format(currentDate));                          
        int age = (currentDt - birthDt) / 10000;

        if (age > 14 || age == 14 ) {
			adultYN = true;
		} else if (age < 14) {
			adultYN = false;
		}
        return adultYN;
    }

}
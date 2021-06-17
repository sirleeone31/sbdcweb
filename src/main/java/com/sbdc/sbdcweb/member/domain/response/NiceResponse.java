package com.sbdc.sbdcweb.member.domain.response;

import lombok.Data;

/**
 * NiceResponse Domain
 * Nice 인증 값을 프런트로 Response
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-10
 */
@Data
public class NiceResponse {
/*
    private String sMessage;
    private String sCipherTime;
    private String sRequestNumber;
    private String sResponseNumber;
    private String sAuthType;
    private String sBirthDate;
    private String sGender;
    private String sNationalInfo;
    private String sMobileCo;
    private String sErrorCode;
*/

	private String sName;
    private String sDupInfo;
    private String sConnInfo;
    private String sMobileNo;
    private int iReturn;
    
    public NiceResponse(String sName, String sDupInfo, String sConnInfo, String sMobileNo, int iReturn) {
    	this.sName = sName;
    	this.sDupInfo = sDupInfo;
    	this.sConnInfo = sConnInfo;
    	this.sMobileNo = sMobileNo;
    	this.iReturn = iReturn;
    }

    public NiceResponse(int iReturn) {
    	this.iReturn = iReturn;
    }
}
package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

/**
 * NiceForm Domain
 * Nice 모듈 사용 인증
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-10
 */
@Data
public class NiceForm {
    private String sMessage;
    private String sEncData;
    private int iReturn;

    public NiceForm(String sMessage, String sEncData, int iReturn) {
    	this.sMessage = sMessage;
        this.sEncData = sEncData;
        this.iReturn = iReturn;
    }

}
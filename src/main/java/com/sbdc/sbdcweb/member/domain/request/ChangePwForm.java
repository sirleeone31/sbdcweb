package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

/**
 * ChangePwForm Domain
 * 비번 찾기, 비번 변경
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class ChangePwForm {
    private String password;
//    private String certDupInfo; // Nice 컬럼 중 sDupInfo
    private String certCi;		// Nice 컬럼 중 sConnInfo
}
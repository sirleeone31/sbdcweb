package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

/**
 * ChangeForm Domain
 * 회원정보 변경
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class ChangeForm {
    private String regNo;
    private String companyName;
    private String ceoName;
    private String managerName;
    private String managerPost;
    private String email;
    private String tel1;
    private String tel2;
    private String tel3;
    private String cel1;
    private String cel2;
    private String cel3;
}
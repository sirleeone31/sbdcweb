package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;

/**
 * SignUpForm Domain
 * 회원가입
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
@Data
public class SignUpForm {
    private String name;
    private String username;
    @Email
    private String email;
    private Set<String> roles;
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
    private Long memberType;
    private String companyName;
    private String ceoName;
    private String managerName;
    private String managerPost;
	private String certDupInfo;
	private String certVirtualNo;
	private String certCi;
	private String sid;
	private String address1;
	private String address2;
	private String tel1;
	private String tel2;
	private String tel3;
	private String cel1;
	private String cel2;
	private String cel3;
	private String mailing;
	private String zipcode5;

	public SignUpForm() {
		// 기본 role -> user, 직원인 경우 admin role 변경
//		this.roles = new HashSet<String>(Arrays.asList("ROLE_USER"));
//		this.roles = new HashSet<String>(Arrays.asList("ROLE_ADMIN"));
	}

}
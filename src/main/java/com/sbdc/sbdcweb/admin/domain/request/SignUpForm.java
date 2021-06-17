package com.sbdc.sbdcweb.admin.domain.request;

import lombok.Data;

import java.util.Set;

/**
 * SignUpForm
 * 회원가입 domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@Data
public class SignUpForm {
    private String username;
    private String password;
    private String name;
    private String enableOuterLogin;
    private String allowedIp;
    private Set<String> roles;

    public SignUpForm() {
		// 기본 role -> user, 직원인 경우 admin role 변경
//		this.roles = new HashSet<String>(Arrays.asList("ROLE_USER"));
//		this.roles = new HashSet<String>(Arrays.asList("ROLE_ADMIN"));
	}

}
package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginForm Domain
 * 로그인
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class LoginForm {
    private String username;
    @NotBlank
    private String password;
    private Long memberType;
}
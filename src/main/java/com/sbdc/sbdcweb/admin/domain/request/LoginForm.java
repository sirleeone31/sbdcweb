package com.sbdc.sbdcweb.admin.domain.request;

import lombok.Data;

/**
 * LoginForm
 * 로그인 시 사용할 domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@Data
public class LoginForm {
    private String username;
    private String password;
}
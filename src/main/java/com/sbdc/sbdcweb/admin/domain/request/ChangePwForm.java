package com.sbdc.sbdcweb.admin.domain.request;

import lombok.Data;

/**
 * ChangeForm
 * 비번 찾기, 비번 변경 시 사용할 domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class ChangePwForm {
    private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
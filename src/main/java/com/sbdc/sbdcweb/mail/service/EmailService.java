package com.sbdc.sbdcweb.mail.service;

import com.sbdc.sbdcweb.service.BaseService;

/**
 * Email Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
public interface EmailService<T, ID> extends BaseService<T, ID> {
	public String getEmailCode();
	public void setEmailCode(String emailCode);
}
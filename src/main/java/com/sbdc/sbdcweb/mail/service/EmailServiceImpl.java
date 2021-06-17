package com.sbdc.sbdcweb.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.mail.repository.EmailRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * Email ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Service
public abstract class EmailServiceImpl<T, ID> extends BaseServiceImpl<T, ID> implements EmailService<T, ID> {
	protected EmailRepository<T, ID> emailRepository;
	protected String emailCode;

	@Autowired
    public EmailServiceImpl(EmailRepository<T, ID> emailRepository) {
    	super(emailRepository);
    	this.emailRepository = emailRepository;
    }

    /**
     * Email 코드 설정 Getter
     */
    @Override
	public String getEmailCode() {
		return emailCode;
	}

    /**
     * Email 코드 설정 Setter
     */
    @Override
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
}
package com.sbdc.sbdcweb.mail.service;

import org.springframework.mail.MailException;

/**
 * Mailing Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
public interface MailingService {
	public Boolean sendMail(String email, String password) throws MailException;
	public Boolean sendMail(String bbsCode, String writer, String subject) throws MailException;
	public Boolean sendMail(String bbsCode, String writer, String subject, String consultType) throws MailException;
}
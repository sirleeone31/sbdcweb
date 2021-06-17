package com.sbdc.sbdcweb.mail.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.mail.domain.CivilEmail;
import com.sbdc.sbdcweb.mail.repository.CivilEmailRepository;

/**
 * 고충처리 부정부패 알림메일발송관리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Service
@Primary
public class CivilEmailServiceImpl extends EmailServiceImpl<CivilEmail, Long> implements CivilEmailService {
	private static final Logger logger = LoggerFactory.getLogger(CivilEmailServiceImpl.class);

	private final CivilEmailRepository civilEmailRepository;
    private final String emailCode = "civilEmail";

	@Autowired
	public CivilEmailServiceImpl(CivilEmailRepository civilEmailRepository) {
		super(civilEmailRepository);
        super.setEmailCode(emailCode);
		this.civilEmailRepository = civilEmailRepository;
	}

	/**
	 * 고충처리 부정부패 알림메일발송관리 자료 수정
     * 
     * @param   id              EMAIL_KEY 값
     * @param   emailRequest    Front에서 입력된 email 자료
     * @return  갱신 로직이 적용된 email 자료
     */
	@Override
	public CivilEmail update(Long id, CivilEmail emailRequest) {
		CivilEmail email = super.select(id);

		if (email != null) {
			if (emailRequest.getEmail() != null && !emailRequest.getEmail().equals("")) {
				email.setEmail(emailRequest.getEmail());
			}
			if (emailRequest.getName() != null && !emailRequest.getName().equals("")) {
				email.setName(emailRequest.getName());
			}
		}

		return super.update(id, email);
	}

    /**
	 * 고충처리 부정부패 알림메일발송관리 자료 삭제
     * 
     * @param   id          EMAIL_KEY 값
     * @return  삭제 로직이 적용된 email 자료
     */
	@Override
	public Map<String, Object> delete(Long id) {
	    Map<String, Object> emailMap = new HashMap<String, Object>();
	    boolean deleteYN = false;
	    CivilEmail email = super.select(id);

		if (email != null) {
	        try {
	        	emailMap.put("emailKey", email.getEmailKey());
	        	civilEmailRepository.delete(email);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("고충처리 부정부패 알림메일 삭제 에러", e.getMessage(), e);
	    	}
		}

		emailMap.put("deleteYN", deleteYN);
	    return emailMap;
	}

}
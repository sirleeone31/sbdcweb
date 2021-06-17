package com.sbdc.sbdcweb.mail.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.mail.domain.QnaEmail;
import com.sbdc.sbdcweb.mail.repository.QnaEmailRepository;

/**
 * 고객상담관리 알림메일발송관리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@Service
public class QnaEmailServiceImpl extends EmailServiceImpl<QnaEmail, Long> implements QnaEmailService {
	private static final Logger logger = LoggerFactory.getLogger(QnaEmailServiceImpl.class);

	private final QnaEmailRepository qnaEmailRepository;
    private final String emailCode = "qnaEmail";

    @Autowired
    public QnaEmailServiceImpl(QnaEmailRepository qnaEmailRepository) {
		super(qnaEmailRepository);
        super.setEmailCode(emailCode);
    	this.qnaEmailRepository = qnaEmailRepository;
    }

	/**
	 * 고객상담관리 알림메일발송관리 자료 수정
     * 
     * @param   id              EMAIL_KEY 값
     * @param   emailRequest    Front에서 입력된 email 자료
     * @return  갱신 로직이 적용된 email 자료
     */
	@Override
	public QnaEmail update(Long id, QnaEmail emailRequest) {
		QnaEmail email = super.select(id);

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
	 * 고객상담관리 알림메일발송관리 자료 삭제
     * 
     * @param   id          EMAIL_KEY 값
     * @return  삭제 로직이 적용된 email 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> emailMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        QnaEmail email = super.select(id);

    	if (email != null) {
            try {
            	emailMap.put("emailKey", email.getEmailKey());
            	qnaEmailRepository.delete(email);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error("고객상담관리 알림메일발송관리 삭제 에러", e.getMessage(), e);
        	}
    	}

    	emailMap.put("deleteYN", deleteYN);
        return emailMap;
    }

}
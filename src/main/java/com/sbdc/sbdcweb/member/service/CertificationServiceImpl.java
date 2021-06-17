package com.sbdc.sbdcweb.member.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.member.domain.Certification;
import com.sbdc.sbdcweb.member.repository.CertificationRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 인증로그 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Service
public class CertificationServiceImpl extends BaseServiceImpl<Certification, Long> implements CertificationService {
	private static final Logger logger = LoggerFactory.getLogger(CertificationServiceImpl.class);

	private final CertificationRepository certificationRepository;

	@Autowired
    public CertificationServiceImpl(CertificationRepository certificationRepository) {
    	super(certificationRepository);
        this.certificationRepository = certificationRepository;
    }

	/**
	 * 인증로그 조회
	 * 
	 * @param 	connInfo	CONN_INFO
	 * @return	조회 로직이 적용된 name 자료
	 */
    @Override
	public String selectCertifi(String connInfo) {
    	String name = null;

    	try {
    		name = certificationRepository.selectMaxNameByConnInfo(connInfo);
    	} catch (NoSuchElementException e) {
            logger.error("인증로그 조회 에러", e.getMessage(), e);
		}

    	return name;
	}

    /**
	 * 인증로그 입력
	 * 
	 * @param 	name		NAME
	 * @param 	dupInfo		DUP_INFO
	 * @param 	connInfo	CONN_INFO
	 * @param 	mobileNo	MEMBER_KEY
	 * @return	입력 로직이 적용된 certification 자료
	 */
    @Override
	public Certification insertCertifi(String name, String dupInfo, String connInfo, String mobileNo) {
    	Certification certification = null;

    	try {
    		certification = certificationRepository.save(new Certification(name, dupInfo, connInfo, mobileNo));
		} catch (Exception e) {
            logger.error("인증로그 입력 에러", e.getMessage(), e);
		}

        return certification;
	}

    /**
	 * 인증로그 삭제
	 * 
	 * @param 	connInfo	CONN_INFO
	 * @return	삭제 로직이 적용된 certification 자료
	 */
    @Override
	public Map<String, Object> deleteCertifi(String connInfo) {
        Map<String, Object> deleteMap = new HashMap<String, Object>();
        boolean deleteYN = false;

        try {
        	certificationRepository.deleteByConnInfo(connInfo);
            certificationRepository.deleteAll();
        	deleteYN = true;
    	} catch (Exception e) {
    		logger.error("삭제 에러", e.getMessage(), e);
    	}

    	deleteMap.put("deleteYN", deleteYN);

    	return deleteMap;
	}

}
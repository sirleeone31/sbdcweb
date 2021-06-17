package com.sbdc.sbdcweb.member.service;

import java.util.Map;

import com.sbdc.sbdcweb.member.domain.Certification;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 인증로그 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
public interface CertificationService extends BaseService<Certification, Long> {
	public String selectCertifi(String connInfo);
	public Certification insertCertifi(String name, String dupInfo, String connInfo, String mobileNo);
	public Map<String, Object> deleteCertifi(String connInfo);
}
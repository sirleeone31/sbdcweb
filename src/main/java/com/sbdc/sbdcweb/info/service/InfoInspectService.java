package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoInspect;

/**
 * 내부감사결과 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
public interface InfoInspectService extends InfoService<InfoInspect, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
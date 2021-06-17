package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoBusi;

/**
 * 사업실명제 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-01
 */
public interface InfoBusiService extends InfoService<InfoBusi, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
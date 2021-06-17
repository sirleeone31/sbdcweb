package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoBusiness;

/**
 * 국외출장정보 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date 	: 2019-11-01
 */
public interface InfoBusinessService extends InfoService<InfoBusiness, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
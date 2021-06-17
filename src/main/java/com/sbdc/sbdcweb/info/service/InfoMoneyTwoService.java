package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoMoneyTwo;

/**
 * 청탁금지법 상담 및 조치 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
public interface InfoMoneyTwoService extends InfoService<InfoMoneyTwo, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
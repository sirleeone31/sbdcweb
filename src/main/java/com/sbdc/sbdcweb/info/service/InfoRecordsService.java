package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoRecords;

/**
 * 기록물분류기준 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-01
 */
public interface InfoRecordsService extends InfoService<InfoRecords, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
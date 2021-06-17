package com.sbdc.sbdcweb.civil.service;

import com.sbdc.sbdcweb.civil.domain.Report;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 민원 및 불공정 행정 - 고충처리 + 부정부패 Service
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-02-13
 */
public interface CivilReportService extends BaseService<Report, Long> {
	public String getInfoCode();

	public void insertIp(String ip, Report reportRequest);
}
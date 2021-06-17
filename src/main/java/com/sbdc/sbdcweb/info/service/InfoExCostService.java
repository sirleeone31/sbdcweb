package com.sbdc.sbdcweb.info.service;

import java.util.List;
import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoExCost;

/**
 * 자율공시 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
public interface InfoExCostService extends InfoService<InfoExCost, Long> {
	public List<InfoExCost> selectInfoList(String type);
	public Map<String, Object> downloadInfo(Long id);

}
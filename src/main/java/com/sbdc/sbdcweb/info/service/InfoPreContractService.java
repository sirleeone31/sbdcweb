package com.sbdc.sbdcweb.info.service;

import java.util.List;

import com.sbdc.sbdcweb.info.domain.response.InfoPreContractAllDto;
import com.sbdc.sbdcweb.info.domain.response.InfoPreContractOneDto;

/**
 * 2017년 이전 계약현황 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
public interface InfoPreContractService {
	public String getInfoCode();

	public List<InfoPreContractAllDto> selectInfoList();
	public InfoPreContractOneDto selectInfo(Long id);
}
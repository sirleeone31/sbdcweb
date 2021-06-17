package com.sbdc.sbdcweb.board.service;

import com.sbdc.sbdcweb.board.domain.BoardContractType;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 입찰정보 카테고리 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
public interface BoardContractTypeService extends BaseService<BoardContractType, Long> {
	public String getBbsCode();

	public Long maxArticleNo();
}
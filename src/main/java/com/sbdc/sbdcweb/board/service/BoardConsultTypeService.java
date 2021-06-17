package com.sbdc.sbdcweb.board.service;

import com.sbdc.sbdcweb.board.domain.BoardConsultType;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 입점 및 판매상담 카테고리 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-19
 */
public interface BoardConsultTypeService extends BaseService<BoardConsultType, Long> {
	public String getBbsCode();

	public Long maxArticleNo();
}
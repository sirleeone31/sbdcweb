package com.sbdc.sbdcweb.info.service;

import java.util.Map;

import com.sbdc.sbdcweb.info.domain.InfoGiftCard;

/**
 * 상품권 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-12
 */
public interface InfoGiftCardService extends InfoService<InfoGiftCard, Long> {
	public Map<String, Object> downloadInfo(Long id);
}
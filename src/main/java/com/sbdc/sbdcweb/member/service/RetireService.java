package com.sbdc.sbdcweb.member.service;

import com.sbdc.sbdcweb.member.domain.Retire;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 회원탈퇴이력 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
public interface RetireService extends BaseService<Retire, Long> {
	public Retire insertRetireLog(Long memberType, String companyName, Long memberKey, String retireComment, String ip);
}
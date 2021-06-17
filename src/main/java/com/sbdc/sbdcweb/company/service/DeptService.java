package com.sbdc.sbdcweb.company.service;

import com.sbdc.sbdcweb.company.domain.Dept;
import com.sbdc.sbdcweb.company.domain.response.DeptOneDto;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 부서 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
public interface DeptService extends BaseService<Dept, Long> {
	public DeptOneDto selectDept(Long id);
}
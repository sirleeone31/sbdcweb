package com.sbdc.sbdcweb.company.service;

import java.util.List;

import com.sbdc.sbdcweb.company.domain.Emp;
import com.sbdc.sbdcweb.company.domain.response.EmpAllDto;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 직원 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
public interface EmpService extends BaseService<Emp, Long> {
	public EmpAllDto selectEmp(Long id);
	public List<EmpAllDto> selectEmpList();
	public List<EmpAllDto> selectEmpByDeptKeyList(Long deptKey);
	public List<EmpAllDto> selectEmpByEmpNameList(String empName);
	public List<EmpAllDto> selectEmpByDeptKeyAndEmpNameList(Long deptKey, String empName);

}
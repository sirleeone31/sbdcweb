package com.sbdc.sbdcweb.company.domain.response;

import lombok.Data;

/**
 * 직원 특정 조회 시 Response DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@Data
public class EmpAllDto {
    private Long empKey;
    private String empName;
    private String empNo;
    private String empTel;
    private Long deptKey;
    private String deptName;
    private Long postKey;
    private String postName;

	public EmpAllDto(Long empKey, String empName, String empNo, 
			String empTel, Long deptKey, String deptName, 
			Long postKey, String postName) {
		this.empKey = empKey;
		this.empName = empName;
		this.empNo = empNo;
		this.empTel = empTel;
		this.deptKey = deptKey;
		this.deptName = deptName;
		this.postKey = postKey;
		this.postName = postName;
	}

}
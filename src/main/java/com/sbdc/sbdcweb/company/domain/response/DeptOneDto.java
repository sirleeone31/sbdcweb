package com.sbdc.sbdcweb.company.domain.response;

import lombok.Data;

/**
 * 부서 특정 조회 시 Response DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@Data
public class DeptOneDto {
    private Long deptKey;
    private String deptName;
    private Long parentKey;
    private String deptTel;
    private String deptFax;
    private Long deptLeader;
    private String deptTask;
    private Long deptType;

	public DeptOneDto(Long deptKey, String deptName, Long parentKey, 
			String deptTel, String deptFax, Long deptLeader, 
			String deptTask, Long deptType) {
		this.deptKey = deptKey;
		this.deptName = deptName;
		this.parentKey = parentKey;
		this.deptTel = deptTel;
		this.deptFax = deptFax;
		this.deptLeader = deptLeader;
		this.deptTask = deptTask;
		this.deptType = deptType;
	}

}
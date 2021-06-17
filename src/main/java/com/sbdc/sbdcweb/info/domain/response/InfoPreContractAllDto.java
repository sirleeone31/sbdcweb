package com.sbdc.sbdcweb.info.domain.response;

import javax.persistence.Transient;

import lombok.Data;

/**
 * 계약현황(~2017년) 클래스
 * 정보공개-사전정보공표-그 밖의 주요 정보-계약 관련 정보(~2017년) 메뉴 선택 시 계약현황 메뉴로 이동
 * 
 * @author 김도현
 */
@Data
public class InfoPreContractAllDto {
	private Long contractKey;
	private String contractName;
	private String requestDept;
	private String startDate;
	private String endDate;
	private String company;

	@Transient
	private long num;

	public InfoPreContractAllDto(Long contractKey,
								 String contractName,
								 String requestDept,
								 String startDate,
								 String endDate,
								 String company) {
        this.contractKey = contractKey;
        this.contractName = contractName;
        this.requestDept = requestDept;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
	}
}
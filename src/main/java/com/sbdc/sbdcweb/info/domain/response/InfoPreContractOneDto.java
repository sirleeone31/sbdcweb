package com.sbdc.sbdcweb.info.domain.response;

import lombok.Data;

/**
 * 계약현황(~2017년) 클래스
 * 정보공개-사전정보공표-그 밖의 주요 정보-계약 관련 정보(~2017년) 메뉴 선택 시 계약현황 메뉴로 이동
 * 
 * @author 김도현
 */
@Data
public class InfoPreContractOneDto {
	private Long contractKey;
    private String contractNo;
	private String contractName;
	private String gubun;
    private Long amount;
    private String hasVat;
	private String startDate;
	private String endDate;
	private String company;
	private String ceo;
	private String contractCause;

	public InfoPreContractOneDto(Long contractKey,
								 String contractNo,
								 String contractName,
								 String gubun,
								 Long amount,
								 String hasVat,
								 String startDate,
								 String endDate,
								 String company,
								 String ceo,
								 String contractCause) {
        this.contractKey = contractKey;
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.gubun = gubun;
        this.amount = amount;
        this.hasVat = hasVat;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
        this.ceo = ceo;
        this.contractCause = contractCause;
	}
}
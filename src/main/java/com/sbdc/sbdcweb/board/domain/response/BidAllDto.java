package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 입찰정보 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-29
 */
@Data
public class BidAllDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String regDate;
    private String endDate;
    private String contractType;
    private String complete;

    public BidAllDto(Long articleKey, Long articleNo, String subject, 
    		String regDate, String endDate, String contractType, String complete) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
        this.regDate = regDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.complete = complete;
    }

    // 메인용 생성자
    public BidAllDto(Long articleKey, String subject, String regDate) {
    	this.articleKey = articleKey;
    	this.subject = subject;
        this.regDate = regDate;
	}
}
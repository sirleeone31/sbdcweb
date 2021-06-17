package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 채용안내 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Data
public class IncruitAllDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String regDate;
    private String endDate;
    private String complete;

    public IncruitAllDto(Long articleKey, Long articleNo, String subject, 
    		String regDate, String endDate, String complete) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
        this.regDate = regDate;
        this.endDate = endDate;
        this.complete = complete;
    }

    // 메인용 생성자
    public IncruitAllDto(Long articleKey, String subject, String regDate) {
    	this.articleKey = articleKey;
    	this.subject = subject;
        this.regDate = regDate;
	}
}
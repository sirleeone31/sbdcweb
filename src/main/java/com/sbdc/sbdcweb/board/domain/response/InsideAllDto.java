package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 내부신고센터 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
@Data
public class InsideAllDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String writer;
    private Long memberKey;
    private String regDate;
    
    public InsideAllDto(Long articleKey, Long articleNo, String subject, 
    		String writer, Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.writer = writer;
    	this.memberKey = memberKey;
    	this.regDate = regDate;
    }
}

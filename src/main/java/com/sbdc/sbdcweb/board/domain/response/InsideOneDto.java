package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 내부신고센터 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
@Data
public class InsideOneDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String content1;
    private String content2;
    private String writer;
    private Long memberKey;
    private String regDate;

    public InsideOneDto(Long articleKey, Long articleNo, String subject, 
    		String content1, String content2, String writer, 
    		Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.content2 = content2;
    	this.writer = writer;
    	this.memberKey = memberKey;
    	this.regDate = regDate;
    }
}

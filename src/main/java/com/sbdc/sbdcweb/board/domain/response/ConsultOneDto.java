package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 입점 및 판매상담 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Data
public class ConsultOneDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String content1;
    private String content2;
    private String writer;
    private Long memberKey;
    private String regDate;
    private String consultType;
    private String secret;

    public ConsultOneDto(Long articleKey, Long articleNo, String subject, 
    		String content1, String content2, String writer, 
    		Long memberKey, String regDate, String consultType, String secret) {
        this.articleKey = articleKey;
        this.articleNo = articleNo;
        this.subject = subject;
        this.content1 = content1;
        this.content2 = content2;
        this.writer = writer;
        this.memberKey = memberKey;
        this.regDate = regDate;
        this.consultType = consultType;
        this.secret = secret;
    }
}
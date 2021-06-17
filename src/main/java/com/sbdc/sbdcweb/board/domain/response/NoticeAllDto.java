package com.sbdc.sbdcweb.board.domain.response;

import lombok.Data;

/**
 * 공지사항 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Data
public class NoticeAllDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String writer;
    private Long memberKey;
    private String regDate;

    public NoticeAllDto(Long articleKey, Long articleNo, String subject, 
    		String writer, Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.writer = writer;
        this.memberKey = memberKey;
    	this.regDate = regDate;
    }

    // 메인용 생성자
    public NoticeAllDto(Long articleKey, String subject, String regDate) {
    	this.articleKey = articleKey;
    	this.subject = subject;
        this.regDate = regDate;
	}
}
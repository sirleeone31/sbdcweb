package com.sbdc.sbdcweb.board.domain.response;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

import lombok.Data;

/**
 * 공지사항 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Data
public class NoticeOneDto {
	private Long articleKey;
    private String bbsCode;
	private Long articleNo;
    private String subject;
    private String content1;
    private String writer;
    private Long memberKey;
    private String regDate;
    private List<String> file;
    private List<BoardAttach> boardAttach;

    public NoticeOneDto(Long articleKey, Long articleNo, String subject, 
    		String content1, String writer, Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
    	this.memberKey = memberKey;
    	this.regDate = regDate;
    }

    // 수정 시 파일용 생성자
    public NoticeOneDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String content1, String writer, 
    		Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
    	this.memberKey = memberKey;
    	this.regDate = regDate;
    }

}
package com.sbdc.sbdcweb.board.domain.response;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

import lombok.Data;

/**
 * 채용안내 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Data
public class IncruitOneDto {
	private Long articleKey;
	private String bbsCode;
	private Long articleNo;
	private String subject;
    private String content1;
    private String writer;
    private String regDate;
    private String endDate;
    private String complete;
    private List<String> file;
    private List<BoardAttach> boardAttach;

    public IncruitOneDto(Long articleKey, Long articleNo, String subject, 
    		String content1, String writer, String regDate, 
    		String endDate, String complete) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
        this.regDate = regDate;
        this.endDate = endDate;
        this.complete = complete;
    }

    // 수정 시 파일용 생성자
    public IncruitOneDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String content1, String writer, 
    		String regDate, String endDate) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
        this.regDate = regDate;
        this.endDate = endDate;
    }
}
package com.sbdc.sbdcweb.board.domain.response;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

import lombok.Data;

/**
 * 입찰정보 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-29
 */
@Data
public class BidOneDto {
	private Long articleKey;
	private String bbsCode;
	private Long articleNo;
    private String subject;
    private String content1;
    private String writer;
    private Long memberKey;
    private String regDate;
    private String endDate;
    private String contractType;
    private String complete;
    private List<String> file;
    private List<BoardAttach> boardAttach;
    
    public BidOneDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String content1, String writer, 
    		Long memberKey, String regDate, String endDate, 
    		String contractType, String complete) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
    	this.memberKey = memberKey;
        this.regDate = regDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.complete = complete;
	}

    // 수정 시 파일용 생성자
    public BidOneDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String content1, String writer, 
    		Long memberKey, String regDate, String endDate, String contractType) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
    	this.memberKey = memberKey;
        this.regDate = regDate;
        this.endDate = endDate;
        this.contractType = contractType;
	}

}
package com.sbdc.sbdcweb.board.domain.response;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

import lombok.Data;

/**
 * 우리제품홍보하기 게시판 특정 게시물 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Data
public class HongboOneDto {
	private Long articleKey;
	private String bbsCode;
	private Long articleNo;
    private String subject;
    private String content1;
    private String writer;
    private Long memberKey;
    private String regDate;
    private String spare1;
    private String spare2;
    private String spare3;
    private String spare4;
    private List<String> file;
    private List<BoardAttach> boardAttach;

    public HongboOneDto(Long articleKey, Long articleNo, String subject, 
    		String content1, String writer, Long memberKey, 
    		String regDate, String spare1, String spare2, 
    		String spare3, String spare4) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
        this.memberKey = memberKey;
        this.regDate = regDate;
        this.spare1 = spare1;
        this.spare2 = spare2;
        this.spare3 = spare3;
        this.spare4 = spare4;
    }

    // 수정 시 파일용 생성자
    public HongboOneDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String content1, String writer, 
    		Long memberKey, String regDate, String spare1, 
    		String spare2, String spare3, String spare4) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.content1 = content1;
    	this.writer = writer;
        this.memberKey = memberKey;
        this.regDate = regDate;
        this.spare1 = spare1;
        this.spare2 = spare2;
        this.spare3 = spare3;
        this.spare4 = spare4;
    }

}
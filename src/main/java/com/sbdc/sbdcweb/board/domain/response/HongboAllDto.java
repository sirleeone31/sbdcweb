package com.sbdc.sbdcweb.board.domain.response;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

import lombok.Data;

/**
 * 우리제품홍보하기 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Data
public class HongboAllDto {
	private Long articleKey;
	private Long articleNo;
    private String subject;
    private String writer;
    private Long memberKey;
    private String regDate;
    private Long attachKey;
    private List<BoardAttach> boardAttach;

    public HongboAllDto(Long articleKey, Long articleNo, String subject, 
    		String writer, Long memberKey, String regDate, Long attachKey) {
    	this.articleKey = articleKey;
    	this.articleNo = articleNo;
    	this.subject = subject;
    	this.writer = writer;
        this.memberKey = memberKey;
        this.regDate = regDate;
        this.attachKey = attachKey;
    }

}
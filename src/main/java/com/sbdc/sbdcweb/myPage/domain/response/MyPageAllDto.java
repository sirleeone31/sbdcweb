package com.sbdc.sbdcweb.myPage.domain.response;

import lombok.Data;

/**
 * MyPage 게시판 전체목록 조회 시 일부 컬럼 조회를 위한 DTO
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-29
 */
@Data
public class MyPageAllDto {
	private Long articleKey;
    private String bbsCode;
	private Long articleNo;
    private String subject;
    private String writer;
    private Long memberKey;
    private String regDate;

    public MyPageAllDto(Long articleKey, String bbsCode, Long articleNo, 
    		String subject, String writer, Long memberKey, String regDate) {
    	this.articleKey = articleKey;
    	this.bbsCode = bbsCode;
    	this.articleNo = articleNo;
        this.subject = subject;
        this.writer = writer;
        this.memberKey = memberKey;
        this.regDate = regDate;
    }

}
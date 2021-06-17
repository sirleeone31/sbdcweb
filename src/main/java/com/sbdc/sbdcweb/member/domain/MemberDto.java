package com.sbdc.sbdcweb.member.domain;

import lombok.Data;

/**
 * MemberDto Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Data
public class MemberDto  {
	private Long memberKey;
	private Long memberType;
	private String username;
	private String regNo;

    public MemberDto(Long memberKey, Long memberType, String username, String regNo) {
        this.memberKey = memberKey;
        this.memberType = memberType;
        this.username = username;
    	this.regNo = regNo;
    }
}
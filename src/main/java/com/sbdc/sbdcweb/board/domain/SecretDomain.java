package com.sbdc.sbdcweb.board.domain;

import lombok.Data;

@Data
public class SecretDomain {
    private Long memberKey;
	private String secret;

    public SecretDomain(Long memberKey, String secret) {
        this.memberKey = memberKey;
    	this.secret = secret;
    }
}
package com.sbdc.sbdcweb.admin.domain;

import lombok.Data;

/**
 * AdminAllDto Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-01
 */
@Data
public class AdminAllDto  {
	private Long adminKey;
    private String username;
	private String name;
	private Long num;

    public AdminAllDto(Long adminKey, String username, String name, Long num) {
        this.adminKey = adminKey;
        this.username = username;
    	this.name = name;
    	this.num = num;
    }
}
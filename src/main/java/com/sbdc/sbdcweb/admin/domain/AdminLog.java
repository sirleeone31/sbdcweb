package com.sbdc.sbdcweb.admin.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * TB_LOG_ADMIN Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-06
 */
@Entity
@Data
@Table(name = "TB_LOG_ADMIN_NEW")
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_KEY")
    private Long logKey;

    @Column(name = "ADMIN_ID")
    private String adminId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "MENT")
    private String ment;

    @Column(name = "IP")
    private String ip;

    @Column(name = "REGDATE")
    private String regDate;

	@Transient
	private long num;

    public AdminLog() {
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public AdminLog(String adminId, String code, String action, String ment, String ip) {
    	this.adminId = adminId;
    	this.code = code;
    	this.action = action;
    	this.ment = ment;
    	this.ip = ip;
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
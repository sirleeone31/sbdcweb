package com.sbdc.sbdcweb.member.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_RETIRE_NEW Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_RETIRE_NEW")
public class Retire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RETIRE_KEY")
	private Long retireKey;

    @Column(name="MEMBER_TYPE")
	private Long memberType;

	@Column(name="COMPANY_NAME")
    private String companyName;

    @Column(name="MEMBER_KEY")
	private Long memberKey;

    @Column(name="RETIRE_REASON")
	private Long retireReason;

    @Column(name="RETIRE_COMMENT")
	private String retireComment;

	@Column(name="IP")
	private String ip;

	@Column(name="RETIRE_DATE")
	private String retireDate;

	public Retire() {}

    public Retire(Long memberType, String companyName, Long memberKey, String retireComment, String ip) {
        this.memberType = memberType;
        this.companyName = companyName;
        this.memberKey = memberKey;
        this.retireComment = retireComment;
        this.ip = ip;

        this.retireReason = 0L;
        this.retireDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
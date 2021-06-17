package com.sbdc.sbdcweb.member.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_MEMBER_CERTI Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_MEMBER_CERTI")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CERTI_KEY")
	private Long certiKey;

	@Column(name="NAME")
	private String name;

	@Column(name="DUP_INFO")
	private String dupInfo;

	@Column(name="CONN_INFO")
	private String connInfo;

	@Column(name="MOBILE_NO")
	private String mobileNo;

	public Certification() {}

    public Certification(String name, String dupInfo, String connInfo, String mobileNo) {
        this.name = name;
        this.dupInfo = dupInfo;
        this.connInfo = connInfo;
        this.mobileNo = mobileNo;
    }
}
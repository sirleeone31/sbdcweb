package com.sbdc.sbdcweb.info.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_CONTRACT Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2019-09-30
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_CONTRACT")
public class InfoPreContract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CONTRACT_KEY")
	private Long contractKey;

	@Column(name="CONTRACT_NO")
	private String contractNo;

	@Column(name="CONTRACT_TYPE")
	private String contractType;

	@Column(name="CONTRACT_NAME")
	private String contractName;

	@Column(name="GUBUN")
	private String gubun;

	@Column(name="REQUEST_DEPT")
	private String requestDept;

	@Column(name="DEPT_MANAGER")
	private String deptManager;

	@Column(name="AMOUNT")
	private Long amount;

	@Column(name="HAS_VAT")
	private String hasVat;

	@Column(name="CONTRACT_DATE")
	private String contractDate;

	@Column(name="STARTDATE")
	private String startDate;

	@Column(name="ENDDATE")
	private String endDate;

	@Column(name="COMPANY")
	private String company;

	@Column(name="CEO")
	private String ceo;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="CONTRACT_CAUSE")
	private String contractCause;

	@Column(name="CONTRACT_SITE")
	private String contractSite;

	@Column(name="BID_TYPE")
	private String bidType;

	@Column(name="ISEXPOSURE")
	private String isExposure;

	@Column(name="REGDATE")
	private String regDate;

	@Column(name="CONTRACT_NO_CHK")
	private String contractNoChk;

	@Transient
	private long num;

	public InfoPreContract() {
	    this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	@Override
	public String toString() {
		return String.valueOf(contractKey);
	}

}
package com.sbdc.sbdcweb.civil.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_CIVIL_REPORT Domain
 * TB_CIVIL_REPORT 컬럼명과 변수명 매핑
 * 생성자에 테이블 기본값 입력
 *
 * @author 	: 김도현
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2019-02-12
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_CIVIL_REPORT")
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPORT_KEY")
	private Long reportKey;
	
	@Column(name = "REPORT_TYPE")
	private String reportType;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "CEL")
	private String cel;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "SUBJECT")
	private String subject;
	
	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "REGDATE")
	private String regDate;

	@Column(name = "MEMBER_NO")
	private Long memberNo;

	public Report() {
		this.name = "";
		this.cel = "";
		this.email = "";
		this.contents = "";
		this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
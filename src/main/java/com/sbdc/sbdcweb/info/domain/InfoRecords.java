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
 * TB_INFORECORDS Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2019-11-01
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_INFORECORDS")
public class InfoRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="INFO_KEY")
	private Long infoKey;

	@Column(name="SUBJECT")
	private String subject;

	@Column(name="DEPTNAME")
	private String deptName;

	@Column(name="GUIDNAME")
	private String guidName;

	@Column(name="FILENAME")
	private String fileName;

	@Column(name="FILEEXT")
	private String fileExt;

	@Column(name="FILESIZE")
	private Long fileSize;

	@Column(name="REGDATE")
	private String regDate;

	@Transient
	private long num;

	public InfoRecords() {
	    this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	@Override
	public String toString() {
		return String.valueOf(infoKey);
	}

}
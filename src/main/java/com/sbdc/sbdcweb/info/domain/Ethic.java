package com.sbdc.sbdcweb.info.domain;

import lombok.Data;

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

/**
 * TB_ETHIC Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2019-09-22
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_ETHIC")
public class Ethic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETHIC_KEY")
    private Long infoKey;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "YYYYMM")
    private String yyyymm;

    @Column(name = "LINK")
    private String link;

    @Column(name = "REGDATE")
    private String regDate;

	@Transient
	private long num;

	public Ethic() {
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

	@Override
	public String toString() {
		return String.valueOf(infoKey);
	}

}
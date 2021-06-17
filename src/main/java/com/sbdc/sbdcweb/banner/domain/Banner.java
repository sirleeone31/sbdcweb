package com.sbdc.sbdcweb.banner.domain;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TB_BANNER Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2018-12-21
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_BANNER")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BANNER_KEY")
    private Long bannerKey;

    @Column(name="TITLE")
    private String title;

    @Column(name="HREF")
    private String href;

    @Column(name="TARGET")
    private String target;

    @Column(name="GUIDNAME")
    private String guidName;

    @Column(name="FILENAME")
    private String fileName;
    
    @Column(name="FILEEXT")
    private String fileExt;

    @Column(name="SEQ")
    private Long seq;

    @Column(name="TYPE")
    private Long type;
    
    @Column(name="REGDATE")
    private String regDate;
    
    public Banner() {
    	this.title = "";
    	this.href = "";
    	this.target = "";
    	this.guidName = "";
    	this.seq = 0L;
    	this.type = 0L;
    	this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
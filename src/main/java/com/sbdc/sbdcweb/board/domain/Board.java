package com.sbdc.sbdcweb.board.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_BBS_ARTICLE Domain
 *
 * @author 	: 이승희
 * @version : 1.0
 * @since 	: 1.0
 * @date 	: 2018-12-11
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_BBS_ARTICLE")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_KEY")
    private Long articleKey;

    @Column(name = "BBS_CODE")
    private String bbsCode;

    @Column(name = "ISNOTICE")
    private String isNotice;

    @Column(name = "ARTICLE_NO")
    private Long articleNo;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "CONTENT1")
    private String content1;

    @Column(name = "CONTENT2")
    private String content2;

    @Column(name = "WRITER")
    private String writer;

    @Column(name = "MEMBER_KEY")
    private Long memberKey;

    @Column(name = "REGDATE")
    private String regDate;

    @Column(name = "ENDDATE")
    private String endDate;

    @Column(name = "CONSULT_TYPE")
    private String consultType;

    @Column(name = "CONTRACT_TYPE")
    private String contractType;

    @Column(name = "SECRET")
    private String secret;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SPARE1")
    private String spare1;

    @Column(name = "SPARE2")
    private String spare2;

    @Column(name = "SPARE3")
    private String spare3;

    @Column(name = "SPARE4")
    private String spare4;

    @Column(name = "SPARE5")
    private String spare5;

    @Column(name = "IP")
    private String ip;

    @Column(name = "HIT")
    private Long hit;

    @Transient
    private List<String> file;

    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_KEY")
    private List<BoardAttach> boardAttach;

    @Transient
    private String complete;

    public Board() {
        this.isNotice = "N";
        this.memberKey = 0L;
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.secret = "N";
        this.hit = 0L;
    }

}
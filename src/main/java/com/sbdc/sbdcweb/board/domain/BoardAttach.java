package com.sbdc.sbdcweb.board.domain;

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

@DynamicUpdate
@Data
@Entity
@Table(name = "TB_BBS_ATTACH")
public class BoardAttach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTACH_KEY", nullable = false)
    private Long attachKey;

    @Column(name = "BBS_CODE")
    private String bbsCode;

    @Column(name = "GUIDNAME")
    private String guidName;

    @Column(name = "ARTICLE_KEY")
    private Long articleKey;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "FILEEXT")
    private String fileExt;

    @Column(name = "FILESIZE")
    private Long fileSize;

    @Column(name = "IS_IMAGE")
    private String isImage;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "REGDATE")
    private String regDate;

    public BoardAttach() {
        this.bbsCode = "";
        this.guidName = "";
        this.articleKey = 0L;
        this.fileName = "";
        this.fileExt = "";
        this.fileSize = 0L;
        this.isImage = "N";
        this.seq = 1L;
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
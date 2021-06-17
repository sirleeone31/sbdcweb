package com.sbdc.sbdcweb.menu.domain;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_MENU Domain
 * 
 * TB_MENU 컬럼명과 변수명 매핑
 * 생성자에 테이블 기본값 입력 
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-31
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MENUKEY", nullable = false)
    private Long menuKey;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="SRC")
    private String src;

    @Column(name="SEQ", nullable = false)
    private Long seq;

    @Column(name="SUB")
    private String sub;

    @Column(name="SUBSEQ")
    private Long subSeq;

	public Menu() {
		this.seq = 0L;
	}
}
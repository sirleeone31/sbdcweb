package com.sbdc.sbdcweb.board.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_BBS_CONTRACTTYPE_NEW Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_BBS_CONTRACTTYPE_NEW")
public class BoardContractType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TYPE_KEY")
    private Long typeKey;

	@Column(name="NAME")
    private String name;

    @Column(name="SEQ")
    private Long seq;

}
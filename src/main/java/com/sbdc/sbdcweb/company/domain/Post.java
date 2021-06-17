package com.sbdc.sbdcweb.company.domain;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_SBDC_POST Domain
 * TB_SBDC_POST 컬럼명과 변수명 매핑
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_SBDC_POST")
public class Post {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POST_KEY")
    private Long postKey;

    @Column(name="POST_NAME")
    private String postName;

    @Column(name="SEQ")
    private Long seq;

    public Post() {
		this.postName = "";
		this.seq = 0L;
	}

}
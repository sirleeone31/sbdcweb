package com.sbdc.sbdcweb.company.domain;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_SBDC_EMP Domain
 * TB_SBDC_EMP 컬럼명과 변수명 매핑
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_SBDC_EMP")
public class Emp {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMP_KEY")
    private Long empKey;

    @Column(name="EMP_NAME")
    private String empName;

    @Column(name="EMP_NO")
    private String empNo;

    @Column(name="EMP_TEL")
    private String empTel;

    @Column(name="DEPT_KEY")
    private Long deptKey;

    @Column(name="POST_KEY")
    private Long postKey;

	public Emp() {
		this.empName = "";
	}

//	ManyToOne JOIN 방식
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Dept dept;

//	ManyToOne JOIN 방식
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "POST_KEY")
//    private Post post;

}
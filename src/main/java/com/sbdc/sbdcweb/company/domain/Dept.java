package com.sbdc.sbdcweb.company.domain;

import lombok.Data;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_SBDC_DEPT Domain
 * TB_SBDC_DEPT 컬럼명과 변수명 매핑
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_SBDC_DEPT")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPT_KEY")
    private Long deptKey;

    @Column(name="DEPT_NAME")
    private String deptName;

    @Column(name="PARENT_KEY")
    private Long parentKey;

    @Column(name="DEPT_TEL")
    private String deptTel;

    @Column(name="DEPT_FAX")
    private String deptFax;

    @Column(name="DEPT_LEADER")
    private Long deptLeader;

    @Column(name="DEPT_TASK")
    private String deptTask;

    @Column(name="DEPT_TYPE")
    private Long deptType;

    @OneToMany
    @JoinTable(name = "TB_SBDC_DEPT",
    joinColumns = @JoinColumn(name = "DEPT_KEY"),
    inverseJoinColumns = @JoinColumn(name = "DEPT_LEADER"))
    private List<Emp> emps;

	public Dept() {
		this.deptName = "";
		this.parentKey = 0L;
		this.deptType = 0L;
	}

}
package com.sbdc.sbdcweb.company.domain;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_SBDC_DEPT_CTE Domain
 * TB_SBDC_DEPT_CTE 컬럼명과 변수명 매핑
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_SBDC_DEPT_CTE")
public class DeptChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPT_KEY")
    private Long deptKey;

    @Column(name="PARENT_KEY")
    private Long parentKey;

    @Column(name="SORT")
    private String sort;

    @Column(name="DEPT_NAME")
    private String deptName;

    @Column(name="LV")
    private Long lv;

}
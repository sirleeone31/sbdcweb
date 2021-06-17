package com.sbdc.sbdcweb.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_ADMIN_AUTH_MASTER_NEW Domain
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_ADMIN_AUTH_MASTER_NEW")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROLE_KEY")
	private Long roleKey;

    @Column(name="CODE")
    private String code;

    @Column(name="AUTH_NAME")
    private String authName;

    public Role() {}

    public Role(Long roleKey, String code, String authName) {
    	this.roleKey = roleKey;
    	this.code = code;
    	this.authName = authName;
    }

}
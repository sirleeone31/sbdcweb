package com.sbdc.sbdcweb.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_ADMIN_AUTH_NEW domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_ADMIN_AUTH_NEW")
public class AdminRole {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AUTH_KEY")
	private Long authKey;

//    @Column(name="ADMIN_ID")
//    private String username;

    @Column(name="ADMIN_KEY")
    private Long adminKey;

    @Column(name="ROLE_KEY")
    private Long roleKey;

    @Column(name="CODE")
    private String code;

    public AdminRole() {}

    public AdminRole(Long authKey, Long adminKey, Long roleKey, String code) {
//    	public AdminRole(Long authKey, String username, Long roleKey, String code) {
    	this.authKey = authKey;
    	this.adminKey = adminKey;
//    	this.username = username;
    	this.roleKey = roleKey;
    	this.code = code;
    }

}
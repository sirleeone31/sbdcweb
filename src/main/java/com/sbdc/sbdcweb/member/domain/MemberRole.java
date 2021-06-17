package com.sbdc.sbdcweb.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * TB_MEMBER_AUTH_NEW domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_MEMBER_AUTH_NEW")
public class MemberRole {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AUTH_KEY")
	private Long authKey;

//    @Column(name="MEMBER_ID")
//    private String username;

    @Column(name="MEMBER_KEY")
    private Long memberKey;

    @Column(name="ROLE_KEY")
    private Long roleKey;

    @Column(name="CODE")
    private String code;

    public MemberRole() {}

    public MemberRole(Long authKey, Long memberKey, Long roleKey, String code) {
//    	public MemberRole(Long authKey, String username, Long roleKey, String code) {
    	this.authKey = authKey;
    	this.memberKey = memberKey;
//    	this.username = username;
    	this.roleKey = roleKey;
    	this.code = code;
    }

}
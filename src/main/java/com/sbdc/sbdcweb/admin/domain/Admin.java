package com.sbdc.sbdcweb.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * TB_ADMIN Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_ADMIN")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADMIN_KEY")
	private Long adminKey;

	@Column(name="ADMIN_ID")
    private String username;

	@Column(name="PASSWORD")
    private String password;

	@Column(name="NAME")
	private String name;

	@Column(name="ENABLE_OUTERLOGIN")
    private String enableOuterLogin;

	@Column(name="LASTLOGIN")
	private String lastLogin;

	@Column(name="LASTIP")
	private String lastIp;

	@Column(name="REGDATE")
	private String regDate;

	@Column(name="ALLOWED_IP")
    private String allowedIp;

	@Column(name="MODIFYDATE")
    private String modifyDate;

	@ManyToMany
	@JoinTable(name = "TB_ADMIN_AUTH_NEW",
	joinColumns = {@JoinColumn(name = "ADMIN_KEY", referencedColumnName = "ADMIN_KEY")},
//	joinColumns = {@JoinColumn(name = "ADMIN_ID", referencedColumnName = "ADMIN_ID")},
	inverseJoinColumns = {
			@JoinColumn(name = "ROLE_KEY", referencedColumnName = "ROLE_KEY"),
			@JoinColumn(name = "CODE", referencedColumnName = "CODE")
			}
	)
	private Set<Role> roles = new HashSet<Role>();

	public Admin() {}

    public Admin(String username, String password, 
    			 String name, String enableOuterLogin, //String lastLogin, 
    			 String lastIp, //String regDate, 
    			 String allowedIp) {//, String modifyDate) {
        this.username = username;
        this.password = password;
    	this.name = name;
    	this.enableOuterLogin = enableOuterLogin;
        this.lastIp = lastIp;
        this.allowedIp = allowedIp;

        // 고정값
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.modifyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
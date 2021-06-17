package com.sbdc.sbdcweb.member.domain;

import lombok.Data;

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
 * TB_MEMBER Domain
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@DynamicUpdate
@Data
@Entity
@Table(name = "TB_MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBER_KEY")
	private Long memberKey;

	@Column(name="MEMBER_NAME")
	private String name;

	@Column(name="COMPANY_NAME")
    private String companyName;

	@Column(name="CEO_NAME")
    private String ceoName;

	@Column(name="MANAGER_NAME")
    private String managerName;

	@Column(name="MANAGER_POST")
    private String managerPost;

	@Column(name="MEMBER_ID")
    private String username;

	@Column(name="REGNO")
    private String regNo;

	@Column(name="EMAIL")
    private String email;

	@Column(name="PASSWORD")
    private String password;

    @Column(name="MEMBER_TYPE")
	private Long memberType;

	@Column(name="CERT_DUPINFO")
	private String certDupInfo;
	
	@Column(name="CERT_VIRTUALNO")
	private String certVirtualNo;
	
	@Column(name="CERT_CI")
	private String certCi;

	@Column(name="SID")
	private String sid;

	@Column(name="ZIPTYPE")
	private Long zipType;

	@Column(name="ZIP1")
	private String zip1;

	@Column(name="ZIP2")
	private String zip2;

	@Column(name="ADDRESS1")
	private String address1;

	@Column(name="ADDRESS2")
	private String address2;

	@Column(name="TEL1")
	private String tel1;

	@Column(name="TEL2")
	private String tel2;

	@Column(name="TEL3")
	private String tel3;

	@Column(name="CEL1")
	private String cel1;

	@Column(name="CEL2")
	private String cel2;

	@Column(name="CEL3")
	private String cel3;

	@Column(name="MAILING")
	private String mailing;

	@Column(name="REGDATE")
	private String regDate;

	@Column(name="LASTLOGIN")
	private String lastLogin;

	@Column(name="LASTIP")
	private String lastIp;

	@Column(name="RETIRE")
	private String retire;

	@Column(name="zipcode5")
	private String zipcode5;

	@ManyToMany
	@JoinTable(name = "TB_MEMBER_AUTH_NEW",
	joinColumns = {@JoinColumn(name = "MEMBER_KEY", referencedColumnName = "MEMBER_KEY")},
//	joinColumns = {@JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")},
	inverseJoinColumns = {
			@JoinColumn(name = "ROLE_KEY", referencedColumnName = "ROLE_KEY"),
			@JoinColumn(name = "CODE", referencedColumnName = "CODE")
			}
	)
	private Set<Role> roles = new HashSet<Role>();

	public Member() {}

    public Member(String name, String username, String email, String password, Long memberType, 
    		String certDupInfo, String certCi,
    		String companyName, String ceoName, String managerName, String managerPost, 
    		String tel1, String tel2, String tel3, 
    		String cel1, String cel2, String cel3, 
    		String lastIp) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.certDupInfo = certDupInfo;
//        this.certVirtualNo = certVirtualNo;
        this.certCi = certCi;
//        this.sid = sid;
//        this.address1 = address1;
//        this.address2 = address2;
        this.companyName = companyName;
        this.ceoName = ceoName;
        this.managerName = managerName;
        this.managerPost = managerPost;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.cel1 = cel1;
        this.cel2 = cel2;
        this.cel3 = cel3;
//        this.mailing = mailing;
        this.lastIp = lastIp;
//        this.zipcode5 = zipcode5;

        // 고정값
        this.zipType = 1L;
        this.zip1 = "000";
        this.zip2 = "000";
        this.address1 = "";
        this.address2 = "";
        this.mailing = "N";
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.retire = "N";
        this.zipcode5 = "00000";
    }

    // 일반회원 생성자
//    public Member(String name, String username, String email, String password, Long memberType, 
//    		String certDupInfo, String certVirtualNo, String certCi, String sid,
//    		String address1, String address2, String tel1, String tel2, String tel3, 
//    		String cel1, String cel2, String cel3, 
//    		String mailing, String lastIp, String zipcode5) {
    public Member(String name, String username, String email, String password, Long memberType, 
    		String certDupInfo, String certCi,
    		String cel1, String cel2, String cel3, String lastIp) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.certDupInfo = certDupInfo;
//        this.certVirtualNo = certVirtualNo;
        this.certCi = certCi;
//        this.sid = sid;
//        this.address1 = address1;
//        this.address2 = address2;
//        this.tel1 = tel1;
//        this.tel2 = tel2;
//        this.tel3 = tel3;
        this.cel1 = cel1;
        this.cel2 = cel2;
        this.cel3 = cel3;
//        this.mailing = mailing;
        this.lastIp = lastIp;
//        this.zipcode5 = zipcode5;

        // 고정값
        this.zipType = 1L;
        this.zip1 = "000";
        this.zip2 = "000";
        this.address1 = "";
        this.address2 = "";
        this.tel1 = "";
        this.tel2 = "";
        this.tel3 = "";
        this.mailing = "N";
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.retire = "N";
        this.zipcode5 = "00000";
    }

    // 기업회원 생성자
//    public Member(String name, String username, String email, String password, Long memberType, 
//    		String ceoName, String managerName, String managerPost, 
//    		String address1, String address2, String tel1, String tel2, String tel3, 
//    		String cel1, String cel2, String cel3, 
//    		String mailing, String lastIp, String zipcode5) {
    public Member(String name, String username, String email, String password, Long memberType, 
     		String ceoName, String managerName, String managerPost, 
      		String tel1, String tel2, String tel3, 
      		String cel1, String cel2, String cel3, 
       		String lastIp) {
    	this.companyName = name;
        this.regNo = username;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.ceoName = ceoName;
        this.managerName = managerName;
        this.managerPost = managerPost;
//        this.address1 = address1;
//        this.address2 = address2;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.cel1 = cel1;
        this.cel2 = cel2;
        this.cel3 = cel3;
//        this.mailing = mailing;
        this.lastIp = lastIp;
//        this.zipcode5 = zipcode5;

        // 고정값
        this.zipType = 1L;
        this.zip1 = "000";
        this.zip2 = "000";
        this.address1 = "";
        this.address2 = "";
        this.mailing = "N";
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.retire = "N";
        this.zipcode5 = "00000";
    }

}
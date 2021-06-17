package com.sbdc.sbdcweb.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.member.domain.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * UserDetailsImpl
 * UserDetails 구현 클래스
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long adminKey;
    private String name;
    private String username;

	private Long memberKey;
    private String email;
    private Long memberType;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * admin 생성자
     *
     * @param adminKey 		adminKey 값
     * @param name 			name 값
     * @param username 		adminId 값
     * @param password 		password 값
     * @param authorities 	권한분류 값
     */
    public UserDetailsImpl(Long adminKey, String name, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    	this.adminKey = adminKey;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * member 생성자
     *
     * @param memberKey 	memberKey 값
     * @param name 			memberName 값
     * @param username 		memberID 값
     * @param password 		password 값
     * @param email 		email 값
     * @param memberType 	회원구분 값(기업회원, 개인회원)
     * @param authorities 	권한분류 값
     */
    public UserDetailsImpl(Long memberKey, String name, String username, String password, String email, Long memberType, Collection<? extends GrantedAuthority> authorities) {
    	this.memberKey = memberKey;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.memberType = memberType;
        this.authorities = authorities;
    }

    /**
     * admin build 함수
     * 
     * @param admin Admin 객체
     */
    public static UserDetailsImpl build(Admin admin) {
        List<GrantedAuthority> authorities = admin.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());

        return new UserDetailsImpl(
        		admin.getAdminKey(),
        		admin.getName(),
        		admin.getUsername(),
        		admin.getPassword(),
                authorities
        );
    }

    /**
     * member build 함수
     * 
     * @param member Member 객체
     */
    public static UserDetailsImpl build(Member member) {
//        List<GrantedAuthority> authorities = member.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        List<GrantedAuthority> authorities = member.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());

//        UserDetailsImpl userDetailsImpl = null;
//
//        if (member.getMemberType() == 1L) {
//            return new UserDetailsImpl(
//            		member.getMemberKey(),
//            		member.getCompanyName(),
//            		member.getRegNo(),
//            		member.getEmail(),
//            		member.getPassword(),
//            		member.getMemberType(),
//                    authorities
//            );
//        } else if (member.getMemberType() == 2L) {
//            return new UserDetailsImpl(
//            		member.getMemberKey(),
//            		member.getName(),
//            		member.getUsername(),
//            		member.getEmail(),
//            		member.getPassword(),
//            		member.getMemberType(),
//                    authorities
//            );
//        }

        return new UserDetailsImpl(
        		member.getMemberKey(),
        		member.getName(),
        		member.getUsername(),
        		member.getEmail(),
        		member.getPassword(),
        		member.getMemberType(),
                authorities
        );
    }


    public Long getAdminKey() {
        return adminKey;
    }

    public Long getMemberKey() {
        return memberKey;
    }

    public String getName() {
		return name;
	}

    public String getEmail() {
        return email;
    }

    public Long getMemberType() {
        return memberType;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        boolean yn = false;
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) o;

        if (adminKey != null) {
            yn = Objects.equals(adminKey, userDetailsImpl.adminKey);        	
        } else if (memberKey != null) {
            yn = Objects.equals(memberKey, userDetailsImpl.memberKey);        	
        }

        return yn;
    }

}
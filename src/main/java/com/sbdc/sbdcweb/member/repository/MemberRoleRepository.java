package com.sbdc.sbdcweb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbdc.sbdcweb.member.domain.Role;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 관리자 권한 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@Repository
public interface MemberRoleRepository extends JpaRepository<Role, Long>, BaseRepository<Role, Long> {
	Role findByCode(String roleName);
}
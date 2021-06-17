package com.sbdc.sbdcweb.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbdc.sbdcweb.admin.domain.AdminLog;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 관리자 로그 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@Repository
public interface AdminLogRepository extends JpaRepository<AdminLog, Long>, BaseRepository<AdminLog, Long> {
	/**
	 * 전체 로그 조회
	 */
	List<AdminLog> findAllByOrderByLogKeyDesc();
}
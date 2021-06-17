package com.sbdc.sbdcweb.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbdc.sbdcweb.member.domain.Retire;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 회원탈퇴이력 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Repository
public interface RetireRepository extends JpaRepository<Retire, Long>, BaseRepository<Retire, Long> {
	/**
	 * 전체 게시물 조회
	 */
	List<Retire> findAllByOrderByRetireKeyDesc();

}
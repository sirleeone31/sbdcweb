package com.sbdc.sbdcweb.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 정보공개 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@NoRepositoryBean
public interface InfoRepository<T, ID> extends JpaRepository<T, ID>, BaseRepository<T, ID> {
	/**
	 * 전체 게시물 조회
	 */
	List<T> findAllByOrderByInfoKeyDesc();

}
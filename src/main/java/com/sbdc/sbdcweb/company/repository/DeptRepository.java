package com.sbdc.sbdcweb.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.company.domain.Dept;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 부서 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RepositoryRestResource
public interface DeptRepository extends JpaRepository<Dept, Long>, BaseRepository<Dept, Long> {
	/**
	 * 전체 부서 조회
	 */
    List<Dept> findAllByOrderByDeptKeyAsc();

}
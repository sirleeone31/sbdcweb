package com.sbdc.sbdcweb.civil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.civil.domain.Report;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 민원 및 불공정 행정 - 고충처리 + 부정부패 Repository
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-02-13
 */
@RepositoryRestResource
public interface CivilReportRepository extends JpaRepository<Report, Long>, BaseRepository<Report, Long> {
	/**
	 * 고충처리 및 부정부패 일부 컬럼만 조회하기(게시판 전체목록 => 관리자영역에서 조회)
	 * @param reportType => civil1 : 고충처리 / civil2 : 부정부패
	 * @author 김도현
	 * @date 2019-02-13
	 * @return
	 */
	List<Report> findAllByOrderByReportKeyDesc();

}
package com.sbdc.sbdcweb.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.company.domain.DeptChart;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 조직도 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@RepositoryRestResource
public interface DeptChartRepository extends JpaRepository<DeptChart, Long>, BaseRepository<DeptChart, Long> {
    /**
     * 전체 조직도 조회
     */
    List<DeptChart> findAllByOrderBySortAsc();

}
package com.sbdc.sbdcweb.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoPreContract;

/**
 * 2017년 이전 계약현황 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@RepositoryRestResource
public interface InfoPreContractRepository extends JpaRepository<InfoPreContract, Long> {
	List<InfoPreContract> findAllByOrderByContractKeyDesc();
}
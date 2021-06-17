package com.sbdc.sbdcweb.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoBusiness;

/**
 * 정보공개 국외출장정보 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date 	: 2019-11-01
 */
@RepositoryRestResource
public interface InfoBusinessRepository extends JpaRepository<InfoBusiness, Long>, InfoRepository<InfoBusiness, Long> {}
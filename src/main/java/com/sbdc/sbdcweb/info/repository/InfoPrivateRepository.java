package com.sbdc.sbdcweb.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoPrivate;

/**
 * 정보공개 수의계약현황 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RepositoryRestResource
public interface InfoPrivateRepository extends JpaRepository<InfoPrivate, Long>, InfoRepository<InfoPrivate, Long> {}
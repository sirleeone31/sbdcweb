package com.sbdc.sbdcweb.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoOpen;

/**
 * 정보공개 구사전정보공표 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RepositoryRestResource
public interface InfoOpenRepository extends JpaRepository<InfoOpen, Long>, InfoRepository<InfoOpen, Long> {}
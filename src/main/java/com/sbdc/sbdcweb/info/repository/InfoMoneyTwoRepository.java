package com.sbdc.sbdcweb.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoMoneyTwo;

/**
 * 정보공개 청탁금지법 상담 및 조치 처리 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RepositoryRestResource
public interface InfoMoneyTwoRepository extends JpaRepository<InfoMoneyTwo, Long>, InfoRepository<InfoMoneyTwo, Long> {}
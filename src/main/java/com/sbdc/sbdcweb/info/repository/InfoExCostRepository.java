package com.sbdc.sbdcweb.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoExCost;

/**
 * 정보공개 자율공시 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RepositoryRestResource
public interface InfoExCostRepository extends JpaRepository<InfoExCost, Long>, InfoRepository<InfoExCost, Long> {
	/**
	 * Type별 게시물 key 내림차순 조회
	 * EC01 : 대표이사 / EC02 : 감사 / EC04 : 이사
	 */
	List<InfoExCost> findByTypeOrderByInfoKeyDesc(String type);

}
package com.sbdc.sbdcweb.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.info.domain.InfoOpenTwo;

/**
 * 정보공개 사전정보공표 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RepositoryRestResource
public interface InfoOpenTwoRepository extends JpaRepository<InfoOpenTwo, Long>, InfoRepository<InfoOpenTwo, Long> {
	/**
	 * Type별 게시물 key 내림차순 조회
	 * a1 : 기관 관련 주요 법령 / a2 : 사규현황 / a3 : 고객만족도 조사 결과
	 * a4 : 국정감사 결과 보고서 / a5 : 임직원 행동강령 및 지침 내용 / a6 : 목동 판매장 레이아웃
	 */
	List<InfoOpenTwo> findByTypeOrderByInfoKeyDesc(String type);

}
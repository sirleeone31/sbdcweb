package com.sbdc.sbdcweb.banner.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.banner.domain.Banner;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 배너 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date 	: 2018-12-21
 */
@RepositoryRestResource
public interface BannerRepository extends JpaRepository<Banner, Long>, BaseRepository<Banner, Long> {
    /**
     * 전체 배너 가져오기
     * SELECT * FROM TB_BANNER ORDER BY TYPE ASC, SEQ ASC
     */
    List<Banner> findAllByOrderByTypeAscSeqAsc();

    /**
     * Type 별 배너 가져오기
     * Type = 0 -> 메인
     * Type = 1 -> 관계(유관)기관
     * Type = 2 -> 배너모음
     * SELECT * FROM TB_BANNER WHERE TYPE = :type ORDER BY SEQ ASC
     */
    List<Banner> findByTypeOrderBySeqAsc(Long type);

    /**
     * 메인 배너 외 가져오기
     * SELECT * FROM TB_BANNER WHERE TYPE IN (:typeList) ORDER BY TYPE ASC, SEQ ASC
     */
    List<Banner> findByTypeInOrderByTypeAscSeqAsc(ArrayList<Long> typeList);

	/**
	 * 최상위 SEQ 조회 - type 조건
	 */
	@Query("SELECT MAX(seq) from Banner WHERE type = :type")
	Long selectMaxSeqByType(@Param("type") Long type);

}
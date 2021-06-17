package com.sbdc.sbdcweb.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.board.domain.BoardConsultType;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 입점 및 판매상담 카테고리 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-19
 */
@RepositoryRestResource
public interface BoardConsultTypeRepository extends JpaRepository<BoardConsultType, Long>, BaseRepository<BoardConsultType, Long> {
	/**
	 * 입점 및 판매상담 카테고리 가져오기
	 * SELECT * FROM TB_BBS_CONSULTTYPE_NEW ORDER BY SEQ ASC
	 */
    List<BoardConsultType> findAllByOrderBySeqAsc();

    /**
	 * 특정 입점 및 판매상담 카테고리 가져오기
	 * SELECT * FROM TB_BBS_CONSULTTYPE_NEW WHERE NAME = :Name
	 */
    List<BoardConsultType> findByName(String Name);

    /**
	 * SEQ sequence 처리
	 * SELECT MAX(SEQ) FROM TB_BBS_CONSULTTYPE
	 */
    @Query("SELECT MAX(seq) FROM BoardConsultType")
    Long selectMaxSeq();

}
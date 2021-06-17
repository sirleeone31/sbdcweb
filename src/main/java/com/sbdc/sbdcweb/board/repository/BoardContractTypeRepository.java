package com.sbdc.sbdcweb.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.board.domain.BoardContractType;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 입찰정보 카테고리 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
@RepositoryRestResource
public interface BoardContractTypeRepository extends JpaRepository<BoardContractType, Long>, BaseRepository<BoardContractType, Long> {
	/**
	 * 입찰정보 카테고리 가져오기
	 * SELECT * FROM TB_BBS_CONTRACTTYPE_NEW ORDER BY SEQ ASC
	 */
    List<BoardContractType> findAllByOrderBySeqAsc();

    /**
	 * SEQ sequence 처리
	 * SELECT MAX(SEQ) FROM TB_BBS_CONTRACTTYPE
	 */
    @Query("SELECT MAX(seq) FROM BoardContractType")
    Long selectMaxSeq();

}
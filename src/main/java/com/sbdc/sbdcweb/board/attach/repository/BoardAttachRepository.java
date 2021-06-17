package com.sbdc.sbdcweb.board.attach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.board.domain.BoardAttach;

/**
 * 첨부파일 Repository
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-29
 */
@RepositoryRestResource
public interface BoardAttachRepository extends JpaRepository<BoardAttach, Long> {
	/**
	 * 전체 첨부파일 조회
	 */
	List<BoardAttach> findAllByOrderByAttachKeyDesc();
	
	/**
	 * 전체 첨부파일 조회 - articleKey 조건
	 */
	List<BoardAttach> findByArticleKeyOrderBySeq(Long articleKey);
	
	/**
	 * 첨부파일 특정 조회 - articleKey 조건
	 */
	BoardAttach findTopByArticleKey(Long articleKey);

	/**
	 * 첨부파일 수 조회 - articleKey 조건
	 */
	Long countByArticleKey(Long articleKey);

	/**
	 * 최상위 ATTACH_KEY 조회
	 */
	@Query("SELECT MAX(attachKey) from BoardAttach")
	Long selectMaxAttachKey();

//	SQL Server 버전 업 후, 쿼리 어노테이션 삭제 후 주석 해지, 위 쿼리 어노테이션, findTop1ByOrderByAttachKeyDesc 삭제
//	BoardAttach findTop1ByOrderByAttachKeyDesc();

	/**
	 * 최상위 SEQ 조회 - articleKey 조건
	 */
	@Query("SELECT MAX(seq) from BoardAttach WHERE articleKey = :articleKey")
	Long selectMaxSeqByArticleKey(@Param("articleKey") Long articleKey);

	/**
	 * 메인화면 중소기업뉴스 이미지 처리를 위한 상위 이미지 정보
	 */
//	@Query(value = "select NEW com.sbdc.sbdcweb.board.domain.BoardAttach(a.articleKey as articleKey, a.attachKey as attachKey, a.guidname as guidname, a.fileext as fileext) from BoardAttach a where a.bbsCode='dongjung' and a.seq=1 order by a.regDate desc")
//	List<BoardAttach> getTop1DongjungAttach();
	List<BoardAttach> findByBbsCodeAndSeqOrderByAttachKeyDesc(String bbsCode, Long seq);
	
//  SQL Server 버전 업 후 PageRequest 로 변경
//	List<BoardAttach> getTop1DongjungAttach(Pageable pageable);

}
package com.sbdc.sbdcweb.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 게시판 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long>, BaseRepository<Board, Long> {
	/**
	 * 전체 게시물 조회
	 */
	List<Board> findByBbsCodeOrderByArticleKeyDesc(String bbsCode);

	/**
     * ARTICLE_NO sequence 처리
     */
    @Query(value = "SELECT MAX(articleNo) FROM Board WHERE bbsCode = :bbsCode")
    Long selectMaxArticleNoByBbsCode(@Param("bbsCode") String bbsCode);

	/**
     * 전체 게시물 특정 순번 조회
     */
//    @Query(value = "SELECT board FROM Board WHERE bbsCode = :bbsCode")
//    List<BidAllDto> selectTop3ByBbsCode(@Param("bbsCode") String bbsCode, Pageable pageable);
    List<Board> findTopByBbsCode(String bbsCode, Pageable pageable);

	/**
     * 비공개 게시물 접근 허용 여부를 위한 memberKey 조회
     */
//    @Query(value = "SELECT NEW com.sbdc.webclientback.board.domain.SecretDomain(bo.secret as secret, bo.memberKey as memberKey) FROM Board WHERE articleKey = :articleKey")
//    Board findByArticleKey(Long articleKey);

    /**
     * HIT 수정
     *
     * @param articleKey	수정 할 ARTICLE_KEY
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_BBS_ARTICLE SET HIT = HIT+1 WHERE ARTICLE_KEY = :articleKey", nativeQuery = true)
    void updateHitByArticleKey(@Param("articleKey") Long articleKey);

    /**
	 * memberKey 등록일 조회
	 */
    @Query("SELECT MAX(regDate) FROM Board WHERE bbsCode = :bbsCode and memberKey = :memberKey GROUP BY memberKey")
    String selectMaxRegDateByBbsCodeAndMemberKey(@Param("bbsCode") String bbsCode,@Param("memberKey") Long memberKey);

}
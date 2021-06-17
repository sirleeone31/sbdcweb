package com.sbdc.sbdcweb.myPage.repository;

import com.sbdc.sbdcweb.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * 마이페이지 Repository
 *
 * @author  : 서지현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-03
 */
@RepositoryRestResource
public interface MyPageRepository extends JpaRepository<Board, Long> {
	List<Board> findByMemberKeyOrderByArticleKeyDesc(Long memberKey);
}
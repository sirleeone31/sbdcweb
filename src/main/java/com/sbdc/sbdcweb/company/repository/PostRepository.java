package com.sbdc.sbdcweb.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.company.domain.Post;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 직급 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long>, BaseRepository<Post, Long> {
	/**
	 * 전체 직급 조회
	 */
    List<Post> findAllByOrderBySeqAsc();

}
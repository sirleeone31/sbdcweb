package com.sbdc.sbdcweb.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.menu.domain.Menu;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 메뉴 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-31
 */
@RepositoryRestResource
public interface MenuRepository extends JpaRepository<Menu, Long>, BaseRepository<Menu, Long> {
    /**
     * 전체 메뉴 가져오기
     * SELECT * FROM TB_MENU ORDER BY SEQ ASC, SUBSEQ ASC
     */
    List<Menu> findAllByOrderBySeqAscSubSeqAsc();

}
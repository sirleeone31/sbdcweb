package com.sbdc.sbdcweb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbdc.sbdcweb.member.domain.Certification;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 인증로그 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long>, BaseRepository<Certification, Long> {
	Certification findByConnInfo(String connInfo);

    /**
	 * connInfo 이름 조회
	 */
    @Query("SELECT MAX(name) FROM Certification WHERE connInfo = :connInfo GROUP BY connInfo")
    String selectMaxNameByConnInfo(@Param("connInfo") String connInfo);

    /**
	 * 임시 인증 삭제
	 */
    @Transactional
    @Modifying
    @Query("DELETE FROM Certification WHERE connInfo = ?1")
    void deleteByConnInfo(String connInfo);

}
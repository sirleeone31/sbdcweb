package com.sbdc.sbdcweb.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.admin.domain.AdminAllDto;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 관리자 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, BaseRepository<Admin, Long> {
    /**
     * adminId 조건으로 Admin 조회
     * 
     * @param 	adminId  ADMIN_ID
     * @return 	조회된 admin 자료
     */
	Admin findByUsername(String adminId);

	/**
     * adminId 존재여부 조회
     * 
     * @param 	adminId  ADMIN_ID
     * @return 	존재여부(true,false)
     */
	boolean existsByUsername(String adminId);

    /**
     * Damo 인코딩
     * 
     * @param 	password	인코딩 전 PASSWORD
     * @return 	인코딩 후 PASSWORD
     */
    @Query(value = "SELECT damo.DBO.hash_str_data(:password)", nativeQuery = true)
    String selectDamoPasswordByPassword(@Param("password") String password);

    /**
     * adminId 조건으로 adminKey 값 찾기
     * 
     * @param 	adminId  ADMIN_ID
     * @return 	조회된 ADMIN_KEY
     */
    @Query(value = "SELECT adminKey FROM Admin WHERE username = :username")
    Long selectAdminKeyByAdminId(@Param("username") String adminId);

 // 검토
    /**
     * adminKey 조건으로 TB_ADMIN_AUTH 테이블의 adminKey 수정 처리
     *
     * @param 	newAdminKey  수정 후 ADMIN_KEY
     * @param 	oldAdminKey  수정 전 ADMIN_KEY
     * @return 	수정된 ROW 값
     */
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE TB_ADMIN_ROLEAUTH SET ADMIN_ID = :newAdminKey WHERE ADMIN_ID = :oldAdminKey", nativeQuery = true)
//    Integer updateNewAdminKeyByOldAdminKey(@Param("newAdminKey") Long newAdminKey, @Param("oldAdminKey") Long oldAdminKey);

    /**
     * lastLogin 수정
     *
     * @param lastLogin 수정 될 LASTLOGIN
     * @param lastIp  	수정 될 LASTIP
     * @param adminKey  수정 할 ADMIN_KEY
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_ADMIN SET LASTLOGIN = :lastLogin, LASTIP = :lastIp WHERE ADMIN_KEY = :adminKey", nativeQuery = true)
    void updateLastLoginLastIpByAdminKey(@Param("lastLogin") String lastLogin, @Param("lastIp") String lastIp, @Param("adminKey") Long adminKey);

    /**
     * 전체 Admin 정보 중 일부 컬럼만 조회
     */
    @Query(value = "SELECT NEW com.sbdc.sbdcweb.admin.domain.AdminAllDto(a.adminKey as adminKey, a.username as username, MIN(a.name) as name, count(*) as num) FROM Admin a JOIN AdminRole b ON a.adminKey = b.adminKey GROUP BY a.adminKey, a.username ORDER BY min(a.regDate) desc")
    List<AdminAllDto> selectAdminAllDtoOrderByRegDateDesc();

}
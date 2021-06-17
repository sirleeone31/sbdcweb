package com.sbdc.sbdcweb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 회원 Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, BaseRepository<Member, Long> {
    /**
     * memberId 조건으로 Member 조회
     * 
     * @param 	memberId  MEMBER_ID
     * @return 	조회된 Member 자료
     */
	Member findByUsername(String memberId);
	Member findByRegNo(String regNo);

	Boolean existsByUsername(String username);
	Boolean existsByRegNo(String regNo);
    Boolean existsByEmail(String email);
    Boolean existsByCertDupInfo(String certDupInfo);

	/**
     * Damo 인코딩
     * 
     * @param 	password	인코딩 전 PASSWORD
     * @return 	인코딩 후 PASSWORD
     */
    @Query(value = "SELECT damo.DBO.hash_str_data(:password)", nativeQuery = true)
    String selectDamoPasswordByPassword(@Param("password") String password);

    /**
     * memberId 조건으로 memberKey 조회
     * 
     * @param 	memberId   MEMBER_ID
     * @return 	조회된 MEMBER_KEY
     */
    @Query(value = "SELECT memberKey FROM Member WHERE username = :username")
    Long selectMemberKeyByMemberId(@Param("username") String username);

    /**
     * memberKey 조건으로 memberId 조회
     * 
     * @param 	memberKey   MEMBER_KEY
     * @return 	조회된 MEMBER_ID
     */
    @Query(value = "SELECT username FROM Member WHERE memberKey = :memberKey")
    String selectUserNameByMemberKey(@Param("memberKey") Long memberKey);

    /**
     * memberKey 조건으로 name 조회
     * 
     * @param 	memberKey   MEMBER_KEY
     * @return 	조회된 MEMBER_NAME 또는 MANAGER_NAME
     */
    @Query(value = "SELECT CASE WHEN name is null THEN managerName ELSE name END as name FROM Member WHERE memberKey = :memberKey")
    String selectMemberNameByMemberKey(@Param("memberKey") Long memberKey);

    /**
     * regNo 조건으로 memberKey 조회
     *
     * @param 	regNo  REGNO
     * @return 	조회된 REGNO
     */
    @Query(value = "SELECT memberKey FROM Member WHERE regNo = :regNo")
    Long selectMemberKeyByRegNo(@Param("regNo") String regNo);

    /**
     * certCi 조건으로 memberId 조회
     *
     * @param 	sConnInfo  Nice 인증 값중 CI 값
     * @return 	조회된 MEMBER_ID
     */
    @Query(value = "SELECT username FROM Member WHERE certCi = :certCi")
	String selectMemberIdByCertCi(@Param("certCi") String sConnInfo);

    // 검토
    /**
     * memberKey 조건으로 TB_MEMBER_AUTH 테이블의 memberKey 수정
     *
     * @param 	newMemberKey  수정 후 MEMBER_KEY
     * @param 	oldMemberKey  수정 전 MEMBER_KEY
     * @return 	수정된 ROW 값
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_MEMBER_AUTH SET MEMBER_KEY = :newMemberKey WHERE MEMBER_KEY = :oldMemberKey", nativeQuery = true)
    Long updateNewAdminKeyByOldAdminKey(@Param("newMemberKey") Long newMemberKey, @Param("oldMemberKey") Long oldMemberKey);
//    void updateMemberAuth(@Param("newMemberKey") Long newMemberKey, @Param("oldMemberKey") Long oldMemberKey);

	/**
     * certCi 조건으로 TB_MEMBER PASSWORD 수정
     *
     * @param password  수정 후 PASSWORD
     * @param certCi  	CERT_CI
     * @return 	수정된 ROW 값
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_MEMBER SET PASSWORD = :password WHERE CERT_CI = :certCi", nativeQuery = true)
    Integer updateMemberPw(@Param("password") String password, @Param("certCi") String certCi);

    /**
     * lastLogin 수정
     *
     * @param lastLogin 수정 될 LASTLOGIN
     * @param lastIp  	수정 될 LASTIP
     * @param adminKey  수정 할 ADMIN_KEY
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_MEMBER SET LASTLOGIN = :lastLogin, LASTIP = :lastIp WHERE MEMBER_KEY = :memberKey", nativeQuery = true)
    void updateLastLoginLastIpByMemberKey(@Param("lastLogin") String lastLogin, @Param("lastIp") String lastIp, @Param("memberKey") Long memberKey);

    /**
     * retire 수정
     *
     * @param lastLogin 수정 될 LASTLOGIN
     * @param lastIp  	수정 될 LASTIP
     * @param adminKey  수정 할 ADMIN_KEY
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_MEMBER SET RETIRE = :retire, RETIRE_REASON = :retireReason, RETIRE_COMMENT = :retireComment, RETIRE_DATE = :retireDate WHERE MEMBER_KEY = :memberKey", nativeQuery = true)
    void updateRetireByMemberKey(@Param("retire") String retire, @Param("retireReason") Long retireReason, @Param("retireComment") String retireComment, @Param("retireDate") String retireDate, @Param("memberKey") Long memberKey);

}
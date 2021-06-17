package com.sbdc.sbdcweb.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.company.domain.Emp;
import com.sbdc.sbdcweb.company.domain.response.EmpAllDto;
import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 직원 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RepositoryRestResource
public interface EmpRepository extends JpaRepository<Emp, Long>, BaseRepository<Emp, Long> {
    /**
     * 전체 직원 조회
     */
    List<Emp> findAllByOrderByPostKeyAscEmpNoAsc();

    /**
     * 전체 직원 일부 컬럼 조회
     */
    @Query(value = "SELECT new com.sbdc.sbdcweb.company.domain.response.EmpAllDto(emp.empKey, emp.empName, emp.empNo, emp.empTel, dept.deptKey, dept.deptName, post.postKey, post.postName) \r\n" + 
    		"  FROM Emp emp \r\n" + 
    		"  JOIN Dept dept \r\n" + 
    		"    ON emp.deptKey  = dept.deptKey\r\n" + 
    		"  JOIN Post post \r\n" + 
    		"    ON emp.postKey  = post.postKey\r\n" + 
            " ORDER BY post.seq,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '196' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '197' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '198' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '199' THEN emp.empNo END DESC,\r\n" +
            " 		   emp.empNo")
    List<EmpAllDto> selectEmpAllDtoOrderBySeqAscEmpNoAsc();

    /**
     * 전체 직원 일부 컬럼 조회 - 부서별 검색(deptKey로 검색)
     */
    @Query(value = "SELECT new com.sbdc.sbdcweb.company.domain.response.EmpAllDto(emp.empKey, emp.empName, emp.empNo, emp.empTel, dept.deptKey, dept.deptName, post.postKey, post.postName) \r\n" + 
    		"  FROM Emp emp \r\n" + 
    		"  JOIN Dept dept \r\n" + 
    		"    ON emp.deptKey  = dept.deptKey\r\n" + 
    		"  JOIN Post post \r\n" + 
    		"    ON emp.postKey  = post.postKey\r\n" + 
            " WHERE 1=1\r\n" + 
            "   AND emp.deptKey = :deptKey\r\n" +
            " ORDER BY post.seq,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '196' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '197' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '198' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '199' THEN emp.empNo END DESC,\r\n" +
            " 		   emp.empNo")
    List<EmpAllDto> selectEmpAllDtoByDeptKeyOrderBySeqAscEmpNoAsc(@Param("deptKey") Long deptKey);

    /**
     * 전체 직원 일부 컬럼 조회 - 이름 검색(empName로 검색)
     */
    @Query(value = "SELECT new com.sbdc.sbdcweb.company.domain.response.EmpAllDto(emp.empKey, emp.empName, emp.empNo, emp.empTel, dept.deptKey, dept.deptName, post.postKey, post.postName) \r\n" + 
    		"  FROM Emp emp \r\n" + 
    		"  JOIN Dept dept \r\n" + 
    		"    ON emp.deptKey  = dept.deptKey\r\n" + 
    		"  JOIN Post post \r\n" + 
    		"    ON emp.postKey  = post.postKey\r\n" + 
            " WHERE 1=1\r\n" + 
            "   AND emp.empName LIKE '%' + :empName + '%'\r\n" + 
            " ORDER BY post.seq,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '196' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '197' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '198' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '199' THEN emp.empNo END DESC,\r\n" +
            " 		   emp.empNo")
    List<EmpAllDto> selectEmpAllDtoByEmpNameOrderBySeqAscEmpNoAsc(@Param("empName") String empName);

    /**
     * 전체 직원 일부 컬럼 조회 - 부서별, 이름 검색(deptKey, empName로 검색)
     */
    @Query(value = "SELECT new com.sbdc.sbdcweb.company.domain.response.EmpAllDto(emp.empKey, emp.empName, emp.empNo, emp.empTel, dept.deptKey, dept.deptName, post.postKey, post.postName) \r\n" + 
    		"  FROM Emp emp \r\n" + 
    		"  JOIN Dept dept \r\n" + 
    		"    ON emp.deptKey  = dept.deptKey\r\n" + 
    		"  JOIN Post post \r\n" + 
    		"    ON emp.postKey  = post.postKey\r\n" + 
            " WHERE 1=1\r\n" + 
            "   AND emp.deptKey = :deptKey\r\n" + 
            "   AND emp.empName LIKE '%' + :empName + '%'\r\n" + 
            " ORDER BY post.seq,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '196' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '197' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '198' THEN emp.empNo END DESC,\r\n" +
            " 		   CASE WHEN SUBSTRING(emp.empNo,1,3) = '199' THEN emp.empNo END DESC,\r\n" +
            " 		   emp.empNo")
    List<EmpAllDto> selectEmpAllDtoByDeptKeyAndEmpNameOrderBySeqAscEmpNoAsc(@Param("deptKey") Long deptKey, @Param("empName") String empName);

    /**
     * 특정 직원 일부 컬럼 조회
     */
    @Query(value = "SELECT new com.sbdc.sbdcweb.company.domain.response.EmpAllDto(emp.empKey, emp.empName, emp.empNo, emp.empTel, dept.deptKey, dept.deptName, post.postKey, post.postName) \r\n" + 
    		"  FROM Emp emp \r\n" + 
    		"  JOIN Dept dept \r\n" + 
    		"    ON emp.deptKey  = dept.deptKey\r\n" + 
    		"  JOIN Post post \r\n" + 
    		"    ON emp.postKey  = post.postKey\r\n" + 
            " WHERE 1=1\r\n" + 
            "   AND emp.empKey = :empKey")
    EmpAllDto selectEmpAllDtoByEmpKey(@Param("empKey") Long empKey);

}
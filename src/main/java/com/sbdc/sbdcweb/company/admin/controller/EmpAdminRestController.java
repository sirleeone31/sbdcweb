package com.sbdc.sbdcweb.company.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.company.domain.Emp;
import com.sbdc.sbdcweb.company.domain.response.EmpAllDto;
import com.sbdc.sbdcweb.company.service.EmpService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 직원 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RestController
@RequestMapping("/admin/company")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class EmpAdminRestController extends BaseRestController<Emp, Long> {
    private final EmpService empService;

    @Autowired
    public EmpAdminRestController(EmpService empService) {
    	super(empService);
    	this.empService = empService;
    }

	/**
     * 직원 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/emp")
    public ResponseEntity<List<EmpAllDto>> selectEmpList(HttpServletRequest request) {
		List<EmpAllDto> empList = empService.selectEmpList();

		if (empList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(empList);
	}

	/**
     * 직원 관리자 특정부서 목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	deptKey		DEPT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/emp/dept/{deptKey}")
    public ResponseEntity<List<EmpAllDto>> selectList(HttpServletRequest request, @PathVariable(name = "deptKey") Long deptKey) {
		List<EmpAllDto> empList = empService.selectEmpByDeptKeyList(deptKey);

		if (empList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(empList);
	}

	/**
     * 직원 관리자 이름 검색 목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	empName		EMP_NAME 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/emp/name/{empName}")
    public ResponseEntity<List<EmpAllDto>> selectList(HttpServletRequest request, @PathVariable(value = "empName") String empName) {
		List<EmpAllDto> empList = empService.selectEmpByEmpNameList(empName);

		if (empList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(empList);
	}

	/**
     * 직원 관리자 이름, 부서 검색 목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	deptKey		DEPT_KEY 값
	 * @param 	empName		EMP_NAME 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/emp/dept/{deptKey}/name/{empName}")
    public ResponseEntity<List<EmpAllDto>> selectList(HttpServletRequest request, @PathVariable(name = "deptKey") Long deptKey, @PathVariable(value = "empName") String empName) {
		List<EmpAllDto> empList = empService.selectEmpByDeptKeyAndEmpNameList(deptKey, empName);

		if (empList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(empList);
	}

	/**
     * 직원 관리자 특정 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMP_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/emp/{id}")
    public ResponseEntity<EmpAllDto> selectEmp(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		EmpAllDto emp = empService.selectEmp(id);

		if (emp == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(emp);
	}

    /**
	 * 직원 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	empRequest		Front에서 입력된 emp 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/emp")
	public ResponseEntity<Emp> insert(HttpServletRequest request, Emp empRequest) {
		Emp emp = empService.insert(empRequest);

		if (emp == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 직원 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				EMP_KEY 값
	 * @param 	empRequest		Front에서 입력된 emp 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/emp/{id}")
	public ResponseEntity<Emp> update(HttpServletRequest request, @PathVariable(value = "id") Long id, Emp empRequest) {
		Emp emp = empService.update(id, empRequest);

		if (emp == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 직원 관리자 삭제
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			EMP_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@DeleteMapping("/emp/{id}")
    public ResponseEntity<Emp> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = empService.delete(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
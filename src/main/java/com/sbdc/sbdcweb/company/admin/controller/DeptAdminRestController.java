package com.sbdc.sbdcweb.company.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.company.domain.Dept;
import com.sbdc.sbdcweb.company.domain.response.DeptOneDto;
import com.sbdc.sbdcweb.company.service.DeptService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 부서 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RestController
@RequestMapping("/admin/company")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class DeptAdminRestController extends BaseRestController<Dept, Long> {
    private final DeptService deptService;

    @Autowired
    public DeptAdminRestController(DeptService deptService) {
    	super(deptService);
    	this.deptService = deptService;
    }

	/**
     * 부서 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dept")
    public ResponseEntity<List<Dept>> selectList(HttpServletRequest request) {
		List<Dept> deptList = deptService.selectList();

		if (deptList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(deptList);
	}

	/**
     * 부서 관리자 특정 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			DEPT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dept/{id}")
    public ResponseEntity<DeptOneDto> selectDept(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		DeptOneDto dept = deptService.selectDept(id);

		if (dept == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(dept);
	}

    /**
	 * 부서 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	deptRequest		Front에서 입력된 dept 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/dept")
	public ResponseEntity<Dept> insert(HttpServletRequest request, Dept deptRequest) {
		Dept dept = deptService.insert(deptRequest);

		if (dept == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 부서 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				DEPT_KEY 값
	 * @param 	deptRequest		Front에서 입력된 dept 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/dept/{id}")
	public ResponseEntity<Dept> update(HttpServletRequest request, @PathVariable(value = "id") Long id, Dept deptRequest) {
		Dept dept = deptService.update(id, deptRequest);

		if (dept == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 부서 관리자 삭제
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			DEPT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@DeleteMapping("/dept/{id}")
    public ResponseEntity<Dept> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = deptService.delete(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
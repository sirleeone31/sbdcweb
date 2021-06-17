package com.sbdc.sbdcweb.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.admin.domain.Role;
import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.admin.service.AdminRoleService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 관리자 권한 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}", maxAge = 3600)
@RestController
@RequestMapping("/admin/api/auth")
public class AdminRoleRestController extends BaseRestController<Role, Long> {
	private final AdminRoleService adminRoleService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

    @Autowired
    public AdminRoleRestController(AdminRoleService adminRoleService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(adminRoleService);
        this.adminRoleService = adminRoleService;
        this.adminLogService = adminLogService;
        this.commonUtils = commonUtils;
        this.jwtProvider = jwtProvider;
    }

    /**
     * 관리자 권한 관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/role")
    public ResponseEntity<List<Role>> selectList(HttpServletRequest request) {
		List<Role> roleList = adminRoleService.selectList();

		if (roleList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminRoleService.getCode(), "목록 조회", "roleKey=" + roleList.get(0).getRoleKey() + " 외 " + (roleList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(roleList);
    }

    /**
	 * 관리자 권한 관리 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ROLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/role/{id}")
    public ResponseEntity<Role> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Role role = adminRoleService.select(id);

		if (role == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminRoleService.getCode(), "조회", "roleKey=" + role.getRoleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    /**
	 * 관리자 권한 관리 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	roleRequest		Front에서 입력된 role 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/role")
    public ResponseEntity<Role> insert(HttpServletRequest request, @RequestBody Role roleRequest) {
    	Role role = adminRoleService.insert(roleRequest);

		if (role == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminRoleService.getCode(), "입력", "roleKey=" + role.getRoleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
	 * 관리자 권한 관리 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ROLE_KEY 값
	 * @param 	roleRequest		Front에서 입력된 role 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/role/{id}")
    public ResponseEntity<Role> update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody Role roleRequest) {
    	Role role = adminRoleService.updateRole(id, roleRequest);

		if (role == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminRoleService.getCode(), "수정", "roleKey=" + role.getRoleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
	 * 관리자 권한 관리 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ROLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/role/{id}")
    public ResponseEntity<Role> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = adminRoleService.deleteRole(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminRoleService.getCode(), "삭제", "roleKey=" + deleteMap.get("roleKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
    }

}
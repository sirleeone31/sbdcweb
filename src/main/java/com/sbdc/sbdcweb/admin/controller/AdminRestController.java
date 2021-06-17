package com.sbdc.sbdcweb.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.admin.domain.AdminAllDto;
import com.sbdc.sbdcweb.admin.domain.request.ChangePwForm;
import com.sbdc.sbdcweb.admin.domain.request.LoginForm;
import com.sbdc.sbdcweb.admin.domain.request.SignUpForm;
import com.sbdc.sbdcweb.admin.domain.response.JwtResponse;
import com.sbdc.sbdcweb.admin.domain.response.ResponseMessage;
import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.admin.service.AdminService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}", maxAge = 3600)
@RestController
@RequestMapping("/admin/api/auth")
public class AdminRestController extends BaseRestController<Admin, Long> {
	private final AdminService adminService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public AdminRestController(AdminService adminService, 
    		AdminLogService adminLogService, 
    		CommonUtils commonUtils, 
    		JwtProvider jwtProvider) {
    	super(adminService);
        this.adminService = adminService;
        this.adminLogService = adminLogService;
        this.commonUtils = commonUtils;
        this.jwtProvider = jwtProvider;
    }

    /**
     * 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/admin")
	public ResponseEntity<List<AdminAllDto>> selectAdminList(HttpServletRequest request) {
		List<AdminAllDto> adminList = adminService.selectAdminList();

		if (adminList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
//		adminLogService.insertAdminLog(jwtProvider.getUserNameFromJwtToken(request.getHeader("AuthToken")), adminService.getCode(), "목록 조회", "[접근권한] adminKey=" + adminList.get(0).getAdminKey() + " 외 " + (adminList.size()-1) + "개", commonUtils.findIp(request));
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode(), "목록 조회", "[접근권한] adminKey=" + adminList.get(0).getAdminKey() + " 외 " + (adminList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(adminList);
	}

	/**
	 * 관리자 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ADMIN_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/admin/{id}")
	public ResponseEntity<Admin> selectAdmin(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Admin admin = adminService.select(id);

		if (admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
				jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode(), "조회", "[접근권한] adminKey=" + admin.getAdminKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(admin);
	}

	/**
	 * 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	signUpRequest	Front에서 입력된 singUp 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/admin")
    public ResponseEntity<Admin> insertAdmin(HttpServletRequest request, @RequestBody SignUpForm signUpRequest) {
		Admin admin = adminService.signUpAdmin(signUpRequest, commonUtils.findIp(request));

		if (admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode(), "등록", "[접근권한] adminKey=" + admin.getAdminKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
	 * 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ADMIN_KEY 값
	 * @param 	signUpRequest	Front에서 입력된 singUp 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/admin/{id}")
	public ResponseEntity<Admin> updateAdmin(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody SignUpForm signUpRequest) {
		Admin admin = adminService.updateAdmin(id, signUpRequest);

		if (admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode(), "수정", "[접근권한] adminKey=" + admin.getAdminKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 관리자 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ADMIN_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<Admin> deleteAdmin(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Map<String, Object> adminMap = adminService.deleteAdmin(id);
    	boolean deleteYN = (boolean) adminMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode(), "삭제", "[접근권한] adminKey=" + adminMap.get("adminKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 관리자 로그인
     * 
     * @param 	request 			HttpServletRequest
     * @param 	loginRequest 		Front에서 입력된 login 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signInAdmin(HttpServletRequest request, @RequestBody LoginForm loginRequest) {
        Map<String, Object> adminMap = adminService.signInAdmin(loginRequest);
        boolean signInYN = (boolean) adminMap.get("signInYN");

    	if (!signInYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

        String jwt = (String) adminMap.get("jwt");
        UserDetails userDetails = (UserDetails) adminMap.get("userDetails");

		// LOG 처리
		adminLogService.insertAdminLog(userDetails.getUsername(), adminService.getCode(), "로그인", "adminKey=" + adminMap.get("adminKey"), commonUtils.findIp(request));
		adminService.updateAdminLog(userDetails.getUsername(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

	/**
	 * mypage 관리자 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ADMIN_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/admin/mypage/{id}")
    public ResponseEntity<Admin> selectAdminMyPage(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Admin admin = adminService.select(id);

		if (admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode() + " mypage", "조회", "adminKey=" + admin.getAdminKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    /**
	 * mypage 관리자 비밀번호 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ADMIN_KEY 값
	 * @param 	changeRequest	Front에서 입력된 change 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/admin/mypage/{id}")
    public ResponseEntity<?> updateAdminPassword(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody ChangePwForm changeRequest) {
    	Map<String, Object> adminMap = adminService.updateAdminPassword(id, changeRequest);

    	String changeMessage = (String) adminMap.get("changeMessage");
        boolean changeYN = (boolean) adminMap.get("changeYN");

        if (!changeYN) {
        	return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseMessage(changeMessage, changeYN));
        }
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				adminService.getCode() + " mypage", "수정", "adminKey=" + adminMap.get("adminKey"), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
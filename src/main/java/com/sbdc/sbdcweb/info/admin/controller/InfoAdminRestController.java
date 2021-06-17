package com.sbdc.sbdcweb.info.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.info.service.InfoService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 정보공개 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@RestController
@RequestMapping("/admin/info")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoAdminRestController<T, ID> extends BaseRestController<T, ID> {
	protected InfoService<T, ID> infoService;
	protected AdminLogService adminLogService;
	protected FileManager fileManager;
	protected CommonUtils commonUtils;
	protected JwtProvider jwtProvider;

	@Autowired
    public InfoAdminRestController(InfoService<T, ID> infoService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(infoService);
    	this.infoService = infoService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 정보공개 관리자 전체목록 조회
     * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	code				infoCode 값
	 * @param 	infoListResponse	response 한 List 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    public ResponseEntity<List<T>> selectInfoList(HttpServletRequest request, String code, List<T> infoListResponse) {
		if (infoListResponse.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "목록 조회", "infoKey=" + infoListResponse.get(0).toString() + " 외 " + (infoListResponse.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(infoListResponse);
	}

    /**
	 * 정보공개 관리자 특정 게시물 조회
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	code			infoCode 값
	 * @param 	infoResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> selectInfo(HttpServletRequest request, ID id, String code, T infoResponse) {
		if (infoResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "조회", "infoKey=" + infoResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(infoResponse);
	}

	/**
	 * 정보공개 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	code			infoCode 값
	 * @param 	infoResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> insertInfo(HttpServletRequest request, T infoRequest, String code, T infoResponse) {
		if (infoResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "입력", "infoKey=" + infoResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 정보공개 관리자 게시물 입력 및 파일 업로드
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력된 파일 객체 
	 * @param 	code			infoCode 값
	 * @param 	infoResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> insertInfo(HttpServletRequest request, T infoRequest, MultipartFile upload, String code, T infoResponse) {
		if (infoResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "입력", "infoKey=" + infoResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 정보공개 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	code			infoCode 값
	 * @param 	infoResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> updateInfo(HttpServletRequest request, ID id, T infoRequest, String code, T infoResponse) {
		if (infoResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "수정", "infoKey=" + infoResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 정보공개 관리자 게시물 수정 및 파일 업로드
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력된 파일 객체 
	 * @param 	code			infoCode 값
	 * @param 	infoResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> updateInfo(HttpServletRequest request, ID id, T infoRequest, MultipartFile upload, String code, T infoResponse) {
		if (infoResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "수정", "infoKey=" + infoResponse.toString(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 정보공개 관리자 게시물 삭제
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	code			infoCode 값
	 * @param 	infoMapResponse	response 한 info 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	public ResponseEntity<T> deleteInfo(HttpServletRequest request, ID id, String code, Map<String,Object> infoMapResponse) {
    	boolean deleteYN = (boolean) infoMapResponse.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

    	adminLogService.insertAdminLog(
    	        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
    			code, "삭제", "infoKey=" + infoMapResponse.get("infoKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
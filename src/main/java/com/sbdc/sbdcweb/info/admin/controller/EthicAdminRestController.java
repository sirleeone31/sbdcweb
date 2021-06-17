package com.sbdc.sbdcweb.info.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.domain.Ethic;
import com.sbdc.sbdcweb.info.service.EthicService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 윤리경영 관리 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@Api(tags = {"1-1-10. 자료실"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class EthicAdminRestController extends InfoAdminRestController<Ethic, Long> {
	private final EthicService ethicService;

	@Autowired
    public EthicAdminRestController(EthicService ethicService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(ethicService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.ethicService = ethicService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 윤리경영 관리 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/ethic")
    public ResponseEntity<List<Ethic>> selectInfoList(HttpServletRequest request){
    	return super.selectInfoList(request, ethicService.getInfoCode(), ethicService.selectInfoList());
	}

	/**
	 * 윤리경영 관리 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ETHIC_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/ethic/{id}")
	public ResponseEntity<Ethic> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, ethicService.getInfoCode(), ethicService.select(id));
	}

    /**
	 * 윤리경영 관리 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/ethic")
	public ResponseEntity<Ethic> insertInfo(HttpServletRequest request, Ethic infoRequest) {
    	return super.insertInfo(request, infoRequest, ethicService.getInfoCode(), ethicService.insert(infoRequest));
	}

	/**
	 * 윤리경영 관리 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ETHIC_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/ethic/{id}")
	public ResponseEntity<Ethic> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, Ethic infoRequest) {
    	return super.updateInfo(request, id, infoRequest, ethicService.getInfoCode(), ethicService.update(id, infoRequest));
	}

	/**
	 * 윤리경영 관리 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ETHIC_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/ethic/{id}")
	public ResponseEntity<Ethic> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, ethicService.getInfoCode(), ethicService.delete(id));
	}

}
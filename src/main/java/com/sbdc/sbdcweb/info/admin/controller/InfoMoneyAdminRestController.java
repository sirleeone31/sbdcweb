package com.sbdc.sbdcweb.info.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.domain.InfoMoney;
import com.sbdc.sbdcweb.info.service.InfoMoneyService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 금품신고결과 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-9 금품신고 결과공개"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoMoneyAdminRestController extends InfoAdminRestController<InfoMoney, Long> {
    private final InfoMoneyService infoMoneyService;

    @Autowired
    public InfoMoneyAdminRestController(InfoMoneyService infoMoneyService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(infoMoneyService, adminLogService, fileManager, commonUtils, jwtProvider);
		this.infoMoneyService = infoMoneyService;
    	this.adminLogService = adminLogService;
		this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 금품신고결과 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/money")
    public ResponseEntity<List<InfoMoney>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoMoneyService.getInfoCode(), infoMoneyService.selectInfoList());
    }

	/**
	 * 금품신고결과 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/money/{id}")
	public ResponseEntity<InfoMoney> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoMoneyService.getInfoCode(), infoMoneyService.select(id));
	}

    /**
	 * 금품신고결과 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/money")
    public ResponseEntity<InfoMoney> insertInfo(HttpServletRequest request, InfoMoney infoRequest, MultipartFile upload) {
    	return super.insertInfo(request, infoRequest, upload, infoMoneyService.getInfoCode(), infoMoneyService.insertInfo(infoRequest, upload));
    }

	/**
	 * 금품신고결과 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/money/{id}")
	public ResponseEntity<InfoMoney> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, InfoMoney infoRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	return super.updateInfo(request, id, infoRequest, upload, infoMoneyService.getInfoCode(), infoMoneyService.updateInfo(id, infoRequest, upload));
    }

	/**
	 * 금품신고결과 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/money/{id}")
    public ResponseEntity<InfoMoney> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, infoMoneyService.getInfoCode(), infoMoneyService.delete(id));
    }

	/**
	 * 금품신고결과 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/money/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoMoneyService.downloadInfo(id);
    	boolean downloadYN = (boolean) infoMap.get("downloadYN");
    	boolean successYN = false;

    	if (downloadYN) {
    		successYN = fileManager.doFileDownload((String) infoMap.get("saveFilename"), (String) infoMap.get("originalFilename"), (String) infoMap.get("pathName"), request, response);
    	}

    	if (!successYN) {
    		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    	} else {
    		return ResponseEntity.status(HttpStatus.OK).build();
    	}
    }

}
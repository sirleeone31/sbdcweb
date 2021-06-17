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
import com.sbdc.sbdcweb.info.domain.InfoPrivate;
import com.sbdc.sbdcweb.info.service.InfoPrivateService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 수의계약현황 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-7 계약현황(수의계약)"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoPrivateAdminRestController extends InfoAdminRestController<InfoPrivate, Long> {
    private final InfoPrivateService infoPrivateService;

    @Autowired
    public InfoPrivateAdminRestController(InfoPrivateService infoPrivateService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(infoPrivateService, adminLogService, fileManager, commonUtils, jwtProvider);
		this.infoPrivateService = infoPrivateService;
    	this.adminLogService = adminLogService;
        this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 수의계약현황 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/private")
    public ResponseEntity<List<InfoPrivate>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoPrivateService.getInfoCode(), infoPrivateService.selectInfoList());
    }

	/**
	 * 수의계약현황 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/private/{id}")
	public ResponseEntity<InfoPrivate> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoPrivateService.getInfoCode(), infoPrivateService.select(id));
	}

    /**
	 * 수의계약현황 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/private")
    public ResponseEntity<InfoPrivate> insertInfo(HttpServletRequest request, InfoPrivate infoRequest, MultipartFile upload) {
    	return super.insertInfo(request, infoRequest, upload, infoPrivateService.getInfoCode(), infoPrivateService.insertInfo(infoRequest, upload));
    }

	/**
	 * 수의계약현황 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/private/{id}")
	public ResponseEntity<InfoPrivate> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, InfoPrivate infoRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	return super.updateInfo(request, id, infoRequest, upload, infoPrivateService.getInfoCode(), infoPrivateService.updateInfo(id, infoRequest, upload));
    }

	/**
	 * 내부감사결과 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/private/{id}")
    public ResponseEntity<InfoPrivate> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, infoPrivateService.getInfoCode(), infoPrivateService.delete(id));
    }

	/**
	 * 내부감사결과 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/private/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoPrivateService.downloadInfo(id);
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
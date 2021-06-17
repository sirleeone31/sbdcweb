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
import com.sbdc.sbdcweb.info.domain.InfoRecords;
import com.sbdc.sbdcweb.info.service.InfoRecordsService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 기록물분류기준 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-01
 */
@Api(tags = {"2-1-13. 기록물분류기준"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoRecordsAdminRestController extends InfoAdminRestController<InfoRecords, Long> {
	private final InfoRecordsService infoRecordsService;

	@Autowired
    public InfoRecordsAdminRestController(InfoRecordsService infoRecordsService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(infoRecordsService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.infoRecordsService = infoRecordsService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
	}

    /**
     * 기록물분류기준 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/records")
    public ResponseEntity<List<InfoRecords>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoRecordsService.getInfoCode(), infoRecordsService.selectInfoList());
    }

	/**
	 * 기록물분류기준 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/records/{id}")
	public ResponseEntity<InfoRecords> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoRecordsService.getInfoCode(), infoRecordsService.select(id));
	}

    /**
	 * 기록물분류기준 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/records")
    public ResponseEntity<InfoRecords> insertInfo(HttpServletRequest request, InfoRecords infoRequest, MultipartFile upload) {
    	return super.insertInfo(request, infoRequest, upload, infoRecordsService.getInfoCode(), infoRecordsService.insertInfo(infoRequest, upload));
    }

	/**
	 * 기록물분류기준 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/records/{id}")
	public ResponseEntity<InfoRecords> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, InfoRecords infoRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	return super.updateInfo(request, id, infoRequest, upload, infoRecordsService.getInfoCode(), infoRecordsService.updateInfo(id, infoRequest, upload));
    }

	/**
	 * 기록물분류기준 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/records/{id}")
    public ResponseEntity<InfoRecords> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, infoRecordsService.getInfoCode(), infoRecordsService.delete(id));
    }

	/**
	 * 기록물분류기준 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/records/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoRecordsService.downloadInfo(id);
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
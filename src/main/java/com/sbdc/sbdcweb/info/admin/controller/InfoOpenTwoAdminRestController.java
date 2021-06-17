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
import com.sbdc.sbdcweb.info.domain.InfoOpenTwo;
import com.sbdc.sbdcweb.info.service.InfoOpenTwoService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 사전정보공표 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-2 사전정보공표"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoOpenTwoAdminRestController extends InfoAdminRestController<InfoOpenTwo, Long> {
    private final InfoOpenTwoService infoOpenTwoService;

    @Autowired
    public InfoOpenTwoAdminRestController(InfoOpenTwoService infoOpenTwoService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
        super(infoOpenTwoService, adminLogService, fileManager, commonUtils, jwtProvider);
        this.infoOpenTwoService = infoOpenTwoService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 사전정보공표 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	type		사전정보공표 타입
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/open-two")
    public ResponseEntity<List<InfoOpenTwo>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoOpenTwoService.getInfoCode(), infoOpenTwoService.selectInfoList());
    }

    /**
     * 사전정보공표 타입별 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	type		사전정보공표 타입
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/open-two/type/{type}")
    public ResponseEntity<List<InfoOpenTwo>> selectInfoTypeList(HttpServletRequest request, @PathVariable(name = "type") String type) {
    	return super.selectInfoList(request, infoOpenTwoService.getInfoCode(), infoOpenTwoService.selectInfoList(type));
    }

	/**
	 * 사전정보공표 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/open-two/{id}")
	public ResponseEntity<InfoOpenTwo> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoOpenTwoService.getInfoCode(), infoOpenTwoService.select(id));
	}

    /**
	 * 사전정보공표 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/open-two")
    public ResponseEntity<InfoOpenTwo> insertInfo(HttpServletRequest request, InfoOpenTwo infoRequest, MultipartFile upload) {
    	return super.insertInfo(request, infoRequest, upload, infoOpenTwoService.getInfoCode(), infoOpenTwoService.insertInfo(infoRequest, upload));
    }

	/**
	 * 사전정보공표 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				INFO_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/open-two/{id}")
	public ResponseEntity<InfoOpenTwo> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, InfoOpenTwo infoRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	return super.updateInfo(request, id, infoRequest, upload, infoOpenTwoService.getInfoCode(), infoOpenTwoService.updateInfo(id, infoRequest, upload));
    }

	/**
	 * 사전정보공표 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/open-two/{id}")
    public ResponseEntity<InfoOpenTwo> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, infoOpenTwoService.getInfoCode(), infoOpenTwoService.delete(id));
    }

	/**
	 * 사전정보공표 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/open-two/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoOpenTwoService.downloadInfo(id);
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
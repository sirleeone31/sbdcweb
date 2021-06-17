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
import com.sbdc.sbdcweb.info.domain.InfoExCost;
import com.sbdc.sbdcweb.info.service.InfoExCostService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 자율공시 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-10 자율공시"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoExCostAdminRestController extends InfoAdminRestController<InfoExCost, Long> {
    private final InfoExCostService infoExCostService;

    @Autowired
    public InfoExCostAdminRestController(InfoExCostService infoExCostService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(infoExCostService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.infoExCostService = infoExCostService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
    }

    /**
     * 자율공시 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	type		자율공시 타입
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/cost")
    public ResponseEntity<List<InfoExCost>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoExCostService.getInfoCode(), infoExCostService.selectInfoList());
    }

    /**
     * 자율공시 타입별 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	type		자율공시 타입
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/cost/type/{type}")
    public ResponseEntity<List<InfoExCost>> selectInfoTypeList(HttpServletRequest request, @PathVariable(name = "type") String type) {
    	return super.selectInfoList(request, infoExCostService.getInfoCode(), infoExCostService.selectInfoList(type));
    }

	/**
	 * 자율공시 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			COST_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/cost/{id}")
	public ResponseEntity<InfoExCost> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoExCostService.getInfoCode(), infoExCostService.select(id));
	}

    /**
	 * 자율공시 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/cost")
    public ResponseEntity<InfoExCost> insertInfo(HttpServletRequest request, InfoExCost infoRequest, MultipartFile upload) {
    	return super.insertInfo(request, infoRequest, upload, infoExCostService.getInfoCode(), infoExCostService.insertInfo(infoRequest, upload));
    }

	/**
	 * 자율공시 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				COST_KEY 값
	 * @param 	infoRequest		Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/cost/{id}")
	public ResponseEntity<InfoExCost> updateInfo(HttpServletRequest request, @PathVariable(value = "id") Long id, InfoExCost infoRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	return super.updateInfo(request, id, infoRequest, upload, infoExCostService.getInfoCode(), infoExCostService.updateInfo(id, infoRequest, upload));
    }

	/**
	 * 자율공시 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			COST_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/cost/{id}")
    public ResponseEntity<InfoExCost> deleteInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteInfo(request, id, infoExCostService.getInfoCode(), infoExCostService.delete(id));
    }

	/**
	 * 자율공시 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			COST_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/cost/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoExCostService.downloadInfo(id);
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
package com.sbdc.sbdcweb.info.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.domain.InfoBusi;
import com.sbdc.sbdcweb.info.service.InfoBusiService;

/**
 * 사업실명제 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-01
 */
@Api(tags = {"2-1-4. 사업실명제"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoBusiRestController extends InfoRestController<InfoBusi, Long> {
	private final InfoBusiService infoBusiService;

	@Autowired
    public InfoBusiRestController(InfoBusiService infoBusiService, FileManager fileManager) {
		super(infoBusiService, fileManager);
    	this.infoBusiService = infoBusiService;
    	this.fileManager = fileManager;
	}

    /**
     * 사업실명제 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/busi")
    public ResponseEntity<List<InfoBusi>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoBusiService.getInfoCode(), infoBusiService.selectInfoList());
    }

	/**
	 * 사업실명제 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/busi/{id}")
	public ResponseEntity<InfoBusi> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoBusiService.getInfoCode(), infoBusiService.select(id));
	}

	/**
	 * 사업실명제 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/busi/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoBusiService.downloadInfo(id);
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
package com.sbdc.sbdcweb.info.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.service.InfoIntroductionService;

/**
 * 기관홍보영상 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2020-08-15
 */
@RestController
@RequestMapping("/info")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoIntroductionRestController {
    private final InfoIntroductionService infoIntroductionService;
    private final FileManager fileManager;

	@Autowired
    public InfoIntroductionRestController(InfoIntroductionService infoIntroductionService, FileManager fileManager) {
    	this.infoIntroductionService = infoIntroductionService;
    	this.fileManager = fileManager;
	}

	/**
	 * 기관홍보영상 첨부파일 다운로드 - 차후 구성
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/download/movie")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> infoMap = infoIntroductionService.downloadInfo();
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
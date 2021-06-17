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
import com.sbdc.sbdcweb.info.domain.InfoRecords;
import com.sbdc.sbdcweb.info.service.InfoRecordsService;

/**
 * 기록물분류기준 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-01
 */
@Api(tags = {"2-1-13. 기록물분류기준"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoRecordsRestController extends InfoRestController<InfoRecords, Long> {
	private final InfoRecordsService infoRecordsService;

	@Autowired
    public InfoRecordsRestController(InfoRecordsService infoRecordsService, FileManager fileManager) {
		super(infoRecordsService, fileManager);
    	this.infoRecordsService = infoRecordsService;
    	this.fileManager = fileManager;
	}

    /**
     * 기록물분류기준 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/records")
    public ResponseEntity<List<InfoRecords>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoRecordsService.getInfoCode(), infoRecordsService.selectInfoList());
    }

	/**
	 * 기록물분류기준 특정 게시물 조회
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
	 * 기록물분류기준 첨부파일 다운로드
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
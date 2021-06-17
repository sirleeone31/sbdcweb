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
import com.sbdc.sbdcweb.info.domain.InfoPrivateTwo;
import com.sbdc.sbdcweb.info.service.InfoPrivateTwoService;

/**
 * 계약현황관리 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-5 계약현황(2017~)"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoPrivateTwoRestController extends InfoRestController<InfoPrivateTwo, Long> {
	private final InfoPrivateTwoService infoPrivateTwoService;

	@Autowired
	public InfoPrivateTwoRestController(InfoPrivateTwoService infoPrivateTwoService, FileManager fileManager) {
		super(infoPrivateTwoService, fileManager);
		this.infoPrivateTwoService = infoPrivateTwoService;
		this.fileManager = fileManager;
	}

	/**
     * 계약현황관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/private-two")
    public ResponseEntity<List<InfoPrivateTwo>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoPrivateTwoService.getInfoCode(), infoPrivateTwoService.selectInfoList());
    }

	/**
	 * 계약현황관리 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/private-two/{id}")
	public ResponseEntity<InfoPrivateTwo> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoPrivateTwoService.getInfoCode(), infoPrivateTwoService.select(id));
	}

	/**
	 * 계약현황관리 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/private-two/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoPrivateTwoService.downloadInfo(id);
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
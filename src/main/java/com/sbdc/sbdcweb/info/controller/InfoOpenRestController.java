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
import com.sbdc.sbdcweb.info.domain.InfoOpen;
import com.sbdc.sbdcweb.info.service.InfoOpenService;

/**
 * 구사전정보공표 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-3 구사전정보공표"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoOpenRestController extends InfoRestController<InfoOpen, Long> {
    private final InfoOpenService infoOpenService;

    @Autowired
    public InfoOpenRestController(InfoOpenService infoOpenService, FileManager fileManager) {
		super(infoOpenService, fileManager);
		this.infoOpenService = infoOpenService;
        this.fileManager = fileManager;
    }

    /**
     * 구사전정보공표 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/open")
    public ResponseEntity<List<InfoOpen>> selectInfoList(HttpServletRequest request) {
    	return super.selectInfoList(request, infoOpenService.getInfoCode(), infoOpenService.selectInfoList());
    }

	/**
	 * 구사전정보공표 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/open/{id}")
	public ResponseEntity<InfoOpen> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, infoOpenService.getInfoCode(), infoOpenService.select(id));
	}

	/**
	 * 구사전정보공표 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			INFO_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/open/download/{id}")
    public ResponseEntity<InputStreamResource> downloadInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id") Long id) {
    	Map<String, Object> infoMap = infoOpenService.downloadInfo(id);
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
package com.sbdc.sbdcweb.info.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.domain.Ethic;
import com.sbdc.sbdcweb.info.service.EthicService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 윤리경영 관리 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@Api(tags = {"1-1-10. 자료실"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class EthicRestController extends InfoRestController<Ethic, Long> {
	private final EthicService ethicService;

	@Autowired
    public EthicRestController(EthicService ethicService, FileManager fileManager) {
		super(ethicService, fileManager);
    	this.ethicService = ethicService;
    	this.fileManager = fileManager;
    }

    /**
     * 윤리경영 관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/ethic")
    public ResponseEntity<List<Ethic>> selectInfoList(HttpServletRequest request){
    	return super.selectInfoList(request, ethicService.getInfoCode(), ethicService.selectInfoList());
	}

	/**
	 * 윤리경영 관리 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ETHIC_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/ethic/{id}")
	public ResponseEntity<Ethic> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.selectInfo(request, id, ethicService.getInfoCode(), ethicService.select(id));
	}

}
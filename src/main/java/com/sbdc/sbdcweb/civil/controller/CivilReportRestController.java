package com.sbdc.sbdcweb.civil.controller;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.civil.domain.Report;
import com.sbdc.sbdcweb.civil.service.CivilReportService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 민원 및 불공정 행정 Controller
 *
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-02-14
 */
@Api(tags = {"1-1-1. 민원 및 불공정"})
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class CivilReportRestController extends BaseRestController<Report, Long> {
	private final CivilReportService civilReportService;
	private final CommonUtils commonUtils;

    @Autowired
	public CivilReportRestController(CivilReportService civilReportService, CommonUtils commonUtils) {
    	super(civilReportService);
		this.civilReportService = civilReportService;
		this.commonUtils = commonUtils;
	}

	/**
	 * 민원 및 불공정행정 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	reportRequest	Front에서 입력된 report 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/report")
	public ResponseEntity<Report> insert(HttpServletRequest request, Report reportRequest) {
		civilReportService.insertIp(commonUtils.findIp(request), reportRequest);
		Report report = civilReportService.insert(reportRequest);

		if (report == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
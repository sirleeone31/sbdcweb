package com.sbdc.sbdcweb.civil.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.civil.domain.Report;
import com.sbdc.sbdcweb.civil.service.CivilReportService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 민원 및 불공정 행정 관리자 Controller
 *
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-02-14
 */
@Api(tags = {"1-1-1. 민원 및 불공정"})
@RestController
@RequestMapping("/admin/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class CivilReportAdminRestController extends BaseRestController<Report, Long> {
	private final CivilReportService civilReportService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
    private final JwtProvider jwtProvider;

    @Autowired
	public CivilReportAdminRestController(CivilReportService civilReportService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(civilReportService);
		this.civilReportService = civilReportService;
		this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

	/**
     * 민원 및 불공정행정 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/report")
	public ResponseEntity<List<Report>> selectList(HttpServletRequest request) {
		List<Report> reportList = civilReportService.selectList();

		if (reportList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		civilReportService.getInfoCode(), "목록 조회", "[접속기록] reportKey=" + reportList.get(0).getReportKey() + " memberNo=" + reportList.get(0).getMemberNo() + " 외 " + (reportList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(reportList);
	}

	/**
	 * 민원 및 불공정행정 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			REPORT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/report/{id}")
	public ResponseEntity<Report> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Report report = civilReportService.select(id);

		if (report == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				civilReportService.getInfoCode(), "조회", "[접속기록] reportKey=" + report.getReportKey() + " memberNo=" + report.getMemberNo(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(report);
	}

	/**
	 * 민원 및 불공정행정 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				REPORT_KEY 값
	 * @param 	boardRequest	Front에서 입력된 report 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/report/{id}")
	public ResponseEntity<Report> update(HttpServletRequest request, @PathVariable(value = "id") Long id, Report reportRequest) {
		Report report = civilReportService.update(id, reportRequest);

		if (report == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				civilReportService.getInfoCode(), "수정", "[접속기록] reportKey=" + report.getReportKey() + " memberNo=" + report.getMemberNo(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();

	}

	/**
	 * 민원 및 불공정행정 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			REPORT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/report/{id}")
	public ResponseEntity<Report> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Map<String, Object> reportMap = civilReportService.delete(id);

		boolean deleteYN = (boolean) reportMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
    	// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				civilReportService.getInfoCode(), "삭제", "[접속기록] reportKey=" + reportMap.get("reportKey") + " memberNo=" + reportMap.get("memberNo"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
package com.sbdc.sbdcweb.info.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.info.domain.response.InfoPreContractAllDto;
import com.sbdc.sbdcweb.info.domain.response.InfoPreContractOneDto;
import com.sbdc.sbdcweb.info.service.InfoPreContractService;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 2017년 이전 계약현황 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Api(tags = {"2-1-6 계약현황(~2017)"})
@RestController
@RequestMapping("/admin/info")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class InfoPreContractAdminRestController {
    private final InfoPreContractService infoPreContractService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public InfoPreContractAdminRestController(InfoPreContractService infoPreContractService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
        this.infoPreContractService = infoPreContractService;
        this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

	/**
     * 이전 계약현황 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/pre")
    public ResponseEntity<List<InfoPreContractAllDto>> selectInfoList(HttpServletRequest request) {
		List<InfoPreContractAllDto> infoList = infoPreContractService.selectInfoList();

		if (infoList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		infoPreContractService.getInfoCode(), "목록 조회", "articleKey=" + infoList.get(0).getContractKey() + " 외 " + infoList.size() + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(infoList);
	}

	/**
	 * 이전 계약현황 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			CONTRACT_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/pre/{id}")
    public ResponseEntity<InfoPreContractOneDto> selectInfo(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	InfoPreContractOneDto info = infoPreContractService.selectInfo(id);

		if (info == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				infoPreContractService.getInfoCode(), "조회", "articleKey=" + info.getContractKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(info);
	}

}
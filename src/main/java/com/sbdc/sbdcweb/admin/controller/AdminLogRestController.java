package com.sbdc.sbdcweb.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.admin.domain.AdminLog;
import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 관리자 로그 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-08
 */
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}", maxAge = 3600)
@RestController
@RequestMapping("/admin/api/auth")
public class AdminLogRestController extends BaseRestController<AdminLog, Long> {
	private final AdminLogService adminLogService;

    @Autowired
    public AdminLogRestController(AdminLogService adminLogService) {
    	super(adminLogService);
        this.adminLogService = adminLogService;
    }

    /**
     * 로그 관리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/log")
    public ResponseEntity<List<AdminLog>> selectList(HttpServletRequest request) {
    	List<AdminLog> adminLogList = adminLogService.selectList();

    	if (adminLogList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(adminLogList);
    }

}
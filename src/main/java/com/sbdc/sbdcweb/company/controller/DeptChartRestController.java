package com.sbdc.sbdcweb.company.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.company.domain.DeptChart;
import com.sbdc.sbdcweb.company.service.DeptChartService;
import com.sbdc.sbdcweb.controller.BaseRestController;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 조직도 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class DeptChartRestController extends BaseRestController<DeptChart, Long> {
    private final DeptChartService deptChartService;

    @Autowired
    public DeptChartRestController(DeptChartService deptChartService) {
    	super(deptChartService);
    	this.deptChartService = deptChartService;
    }

	/**
     * 조직도 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/deptChart")
    public ResponseEntity<List<DeptChart>> selectList(HttpServletRequest request) {
		List<DeptChart> deptChartList = deptChartService.selectList();

		if (deptChartList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(deptChartList);
	}

}
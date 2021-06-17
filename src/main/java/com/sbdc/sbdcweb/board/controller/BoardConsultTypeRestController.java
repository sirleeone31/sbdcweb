package com.sbdc.sbdcweb.board.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.board.domain.BoardConsultType;
import com.sbdc.sbdcweb.board.service.BoardConsultTypeService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 입점 및 판매상담 카테고리 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-19
 */
@Api(tags = {"1-2-2. 입점 및 판매상담 카테고리"})
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardConsultTypeRestController extends BaseRestController<BoardConsultType, Long> {
	private final BoardConsultTypeService boardConsultTypeService;

	@Autowired
	public BoardConsultTypeRestController(BoardConsultTypeService boardConsultTypeService) {
		super(boardConsultTypeService);
		this.boardConsultTypeService = boardConsultTypeService;
	}

	/**
     * 입점 및 판매상담 카테고리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/consult-type")
	public ResponseEntity<List<BoardConsultType>> selectList(HttpServletRequest request) {
		List<BoardConsultType> boardList = boardConsultTypeService.selectList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
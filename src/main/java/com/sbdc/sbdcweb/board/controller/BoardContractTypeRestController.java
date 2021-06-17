package com.sbdc.sbdcweb.board.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.board.domain.BoardContractType;
import com.sbdc.sbdcweb.board.service.BoardContractTypeService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 입찰정보 카테고리 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
@Api(tags = {"1-2-3. 입찰정보 카테고리"})
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardContractTypeRestController extends BaseRestController<BoardContractType, Long> {
    private final BoardContractTypeService boardContractTypeService;

	@Autowired
    public BoardContractTypeRestController(BoardContractTypeService boardContractTypeService) {
		super(boardContractTypeService);
		this.boardContractTypeService = boardContractTypeService;
    }

	/**
     * 입찰정보 카테고리 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/contract-type")
    public ResponseEntity<List<BoardContractType>> selectList(HttpServletRequest request) {
		List<BoardContractType> boardList = boardContractTypeService.selectList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
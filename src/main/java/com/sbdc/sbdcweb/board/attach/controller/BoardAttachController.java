package com.sbdc.sbdcweb.board.attach.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.AttachAllDto;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 첨부파일 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-29
 */
@Api(tags = {"1-2-1. 첨부파일"})
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardAttachController {

	private final BoardAttachService boardAttachService;

	@Autowired
	public BoardAttachController(BoardAttachService boardAttachService, FileManager fileManager) {
		this.boardAttachService = boardAttachService;
	}

	/**
     * 첨부파일 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/attach")
    public ResponseEntity<List<BoardAttach>> selectBoardAttachList(HttpServletRequest request) {
		List<BoardAttach> boardAttachList = boardAttachService.selectBoardAttachList();

		if (boardAttachList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardAttachList);
	}

    /**
     * 첨부파일 전체목록 조회 - articleKey 조건
     * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	articleKey			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/attach/{articleKey}")
    public ResponseEntity<List<BoardAttach>> selectBoardAttachList(HttpServletRequest request, @PathVariable(value = "articleKey") Long articleKey) {
		List<BoardAttach> boardAttachList = boardAttachService.selectBoardAttachList(articleKey);

		if (boardAttachList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardAttachList);
	}

    /**
	 * 첨부파일 삭제
	 * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	id					ATTACH_KEY 값 
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@DeleteMapping("/attach/{id}")
	public ResponseEntity<BoardAttach> deleteBoardAttach(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = boardAttachService.deleteBoardAttach(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
	 * 첨부파일 메인 일부목록 조회
	 * 
	 * @param 	request				HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/attach-main")
	public ResponseEntity<List<AttachAllDto>> selectBoardAttachListMain(HttpServletRequest request) {
		List<AttachAllDto> boardAttachList= boardAttachService.selectBoardAttachListMain();

		if (boardAttachList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardAttachList);
	}	

}
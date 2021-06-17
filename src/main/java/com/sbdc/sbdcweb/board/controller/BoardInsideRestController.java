package com.sbdc.sbdcweb.board.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.SecretDomain;
import com.sbdc.sbdcweb.board.domain.response.InsideAllDto;
import com.sbdc.sbdcweb.board.domain.response.InsideOneDto;
import com.sbdc.sbdcweb.board.service.BoardInsideService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 내부신고센터 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
@Api(tags = {"1-1-7. 내부신고센터"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardInsideRestController extends BoardRestController {
	private final BoardInsideService boardInsideService;
	private final CommonUtils commonUtils;

	@Autowired
	public BoardInsideRestController(BoardInsideService boardInsideService, FileManager fileManager, CommonUtils commonUtils) {
		super(boardInsideService, fileManager, commonUtils);
		this.boardInsideService = boardInsideService;
		this.fileManager = fileManager;
		this.commonUtils = commonUtils;
	}

	/**
     * 내부신고센터 게시판 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/inside")
	public ResponseEntity<List<InsideAllDto>> selectBoardInsideList(HttpServletRequest request) {
		List<InsideAllDto> boardList = boardInsideService.selectBoardInsideList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 내부신고센터 게시판 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/inside/{id}")
	public ResponseEntity<InsideOneDto> selectBoardInside(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		InsideOneDto board = boardInsideService.selectBoardInside(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 내부신고센터 게시판 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	게시판 Controller insertBoard
	 */
	@PostMapping("/inside")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest) {
		boardInsideService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardInsideService.getBbsCode(), boardInsideService.insertBoard(boardRequest));
	}

	/**
	 * 내부신고센터 게시판 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	게시판 Controller updateBoard
	 */
	@PatchMapping("/inside/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest) {
		return super.updateBoard(request, id, boardRequest, boardInsideService.getBbsCode(), boardInsideService.update(id, boardRequest, role));
	}

	/**
	 * 내부신고센터 게시판 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	게시판 Controller deleteBoard
	 */
	@DeleteMapping("/inside/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardInsideService.getBbsCode(), boardInsideService.delete(id));
	}

    /**
	 * 내부신고센터 게시판 회원 비밀글 조회
     *
	 * @param 	request		HttpServletRequest 객체
     * @param 	id 			ARTICLE_KEY 값
     * @return 	BoardRestController secretArticle
     */
	@GetMapping("/inside/auth/{id}")
	public ResponseEntity<SecretDomain> secretArticle(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.secretArticle(request, id);
    }

}
package com.sbdc.sbdcweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sbdc.sbdcweb.board.domain.SecretDomain;
import com.sbdc.sbdcweb.board.domain.response.EqnaAllDto;
import com.sbdc.sbdcweb.board.domain.response.EqnaOneDto;
import com.sbdc.sbdcweb.board.service.BoardEqnaService;

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

import com.sbdc.sbdcweb.board.controller.BoardRestController;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 윤리경영 QNA 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Api(tags = {"1-1-4. 윤리경영 Q&A"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardEqnaRestController extends BoardRestController {
	private final BoardEqnaService boardEqnaService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
	public BoardEqnaRestController(BoardEqnaService boardEqnaService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardEqnaService, fileManager, commonUtils);
		this.boardEqnaService = boardEqnaService;
		this.fileManager = fileManager;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

	/**
     * 윤리경영 QNA 게시판 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/eqna")
	public ResponseEntity<List<EqnaAllDto>> selectBoardEqnaList(HttpServletRequest request) {
		List<EqnaAllDto> boardList = boardEqnaService.selectBoardEqnaList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 윤리경영 QNA 게시판 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/eqna/{id}")
	public ResponseEntity<EqnaOneDto> selectBoardEqna(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		EqnaOneDto board = boardEqnaService.selectBoardEqna(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 윤리경영 QNA 게시판 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	BoardRestController insertBoard
	 */
	@PostMapping("/eqna")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest) {
		boardEqnaService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardEqnaService.getBbsCode(), boardEqnaService.insertBoard(boardRequest));
	}

	/**
	 * 윤리경영 QNA 게시판 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	BoardRestController updateBoard
	 */
	@PatchMapping("/eqna/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest) {
	    Long memberKey = jwtProvider.getUserKeyFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
		return super.updateBoard(request, id, boardRequest, boardEqnaService.getBbsCode(), boardEqnaService.update(id, boardRequest, role, memberKey));
	}

	/**
	 * 윤리경영 QNA 게시판 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@DeleteMapping("/eqna/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
	    Long memberKey = jwtProvider.getUserKeyFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
		return super.deleteBoard(request, id, boardEqnaService.getBbsCode(), boardEqnaService.delete(id, memberKey));
	}

    /**
	 * 윤리경영 QNA 게시판 회원 비밀글 조회
     *
	 * @param 	request		HttpServletRequest 객체
     * @param 	id 			ARTICLE_KEY 값
     * @return 	BoardRestController secretArticle
     */
	@GetMapping("/eqna/auth/{id}")
	public ResponseEntity<SecretDomain> secretArticle(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.secretArticle(request, id);
    }

}
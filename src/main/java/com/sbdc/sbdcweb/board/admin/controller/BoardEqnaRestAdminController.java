package com.sbdc.sbdcweb.board.admin.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 윤리경영 QNA 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Api(tags = {"1-1-4. 윤리경영 Q&A"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardEqnaRestAdminController extends BoardRestAdminController {
	private final BoardEqnaService boardEqnaService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
	public BoardEqnaRestAdminController(BoardEqnaService boardEqnaService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardEqnaService, adminLogService, fileManager, commonUtils, jwtProvider);
		this.boardEqnaService = boardEqnaService;
		this.adminLogService = adminLogService;
		this.fileManager = fileManager;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

	/**
     * 윤리경영 QNA 게시판 관리자 전체목록 조회
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

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardEqnaService.getBbsCode(), "목록 조회", "articleKey=" + boardList.get(0).getArticleKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 윤리경영 QNA 게시판 관리자 특정 게시물 조회
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

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardEqnaService.getBbsCode(), "조회", "articleKey=" + board.getArticleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 윤리경영 QNA 게시판 관리자 게시물 수정
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
	 * 윤리경영 QNA 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@DeleteMapping("/eqna/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardEqnaService.getBbsCode(), boardEqnaService.delete(id));
	}

    /**
	 * 윤리경영 QNA 게시판 관리자 회원 비밀글 조회
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
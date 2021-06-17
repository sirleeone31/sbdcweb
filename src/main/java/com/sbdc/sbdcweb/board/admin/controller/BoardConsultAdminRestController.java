package com.sbdc.sbdcweb.board.admin.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.response.ConsultAllDto;
import com.sbdc.sbdcweb.board.domain.response.ConsultOneDto;
import com.sbdc.sbdcweb.board.service.BoardConsultService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 입점 및 판매상담 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Api(tags = {"1-1-2. 입점 및 판매상담"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardConsultAdminRestController extends BoardRestAdminController {
	private final BoardConsultService boardConsultService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

    @Autowired
    public BoardConsultAdminRestController(BoardConsultService boardConsultService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardConsultService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.boardConsultService = boardConsultService;
		this.adminLogService = adminLogService;
		this.fileManager = fileManager;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
    }

	/**
     * 입점 및 판매상담 게시판 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/consult")
    public ResponseEntity<List<ConsultAllDto>> selectBoardConsultList(HttpServletRequest request) {
		List<ConsultAllDto> boardList = boardConsultService.selectBoardConsultList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardConsultService.getBbsCode(), "목록 조회", "articleKey=" + boardList.get(0).getArticleKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
    }

	/**
	 * 입점 및 판매상담 게시판 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/consult/{id}")
    public ResponseEntity<ConsultOneDto> selectBoardConsult(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	ConsultOneDto board = boardConsultService.selectBoardConsult(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardConsultService.getBbsCode(), "조회", "articleKey=" + board.getArticleKey(), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 입점 및 판매상담 게시판 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	BoardRestController updateBoard
	 */
	@PatchMapping("/consult/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest) {
		Long memberKey = jwtProvider.getUserKeyFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
		return super.updateBoard(request, id, boardRequest, boardConsultService.getBbsCode(), boardConsultService.update(id, boardRequest, role, memberKey));
	}

	/**
	 * 입점 및 판매상담 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@DeleteMapping("/consult/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardConsultService.getBbsCode(), boardConsultService.delete(id));
	}

}
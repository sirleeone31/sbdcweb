package com.sbdc.sbdcweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.SecretDomain;
import com.sbdc.sbdcweb.board.domain.response.QnaAllDto;
import com.sbdc.sbdcweb.board.domain.response.QnaOneDto;
import com.sbdc.sbdcweb.board.service.BoardQnaService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객상담 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
@Api(tags = {"1-1-9. 고객상담"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardQnaRestController extends BoardRestController {
	private final BoardQnaService boardQnaService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
	public BoardQnaRestController(BoardQnaService boardQnaService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardQnaService, fileManager, commonUtils);
		this.boardQnaService = boardQnaService;
		this.fileManager = fileManager;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
	}

	/**
     * 고객상담 게시판 전체목록 조회
     * 
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/qna")
	public ResponseEntity<List<QnaAllDto>> selectBoardQnaList(HttpServletRequest request) {
		List<QnaAllDto> boardList = boardQnaService.selectBoardQnaList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 고객상담 게시판 특정 게시물 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/qna/{id}")
	public ResponseEntity<QnaOneDto> selectBoardEqna(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		QnaOneDto board = boardQnaService.selectBoardQna(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 고객상담 게시판 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	BoardRestController insertBoard
	 */
	@PostMapping("/qna")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest) {
		boardQnaService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardQnaService.getBbsCode(), boardQnaService.insertBoard(boardRequest));
	}

	/**
	 * 고객상담 게시판 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	BoardRestController updateBoard
	 */
	@PatchMapping("/qna/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest) {
		// 불충분한 사용자 인가 조치
	    Long memberKey = jwtProvider.getUserKeyFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
		return super.updateBoard(request, id, boardRequest, boardQnaService.getBbsCode(), boardQnaService.update(id, boardRequest, role, memberKey));
	}

	/**
	 * 고객상담 게시판 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	게시판 Controller deleteBoard
	 */
	@DeleteMapping("/qna/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		// 불충분한 사용자 인가 조치
		Long memberKey = jwtProvider.getUserKeyFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
		return super.deleteBoard(request, id, boardQnaService.getBbsCode(), boardQnaService.delete(id, memberKey));
	}

    /**
	 * 고객상담 게시판 회원 비밀글 조회
     *
	 * @param 	request		HttpServletRequest 객체
     * @param 	id 			ARTICLE_KEY 값
     * @return 	BoardRestController secretArticle
     */
	@GetMapping("/qna/auth/{id}")
    public ResponseEntity<SecretDomain> secretArticle(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.secretArticle(request, id);
    }

    /**
	 * 고객상담 게시판 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값
	 * @param 	thumb		썸네일 사용 여부
	 * @return	BoardRestController	selectImage
	 */
    @GetMapping("/qna/image/{attachKey}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "attachKey") Long attachKey, @RequestParam(value = "thumb", required = false) String thumb) {
    	return super.selectImage(request, attachKey, thumb);
    }

}
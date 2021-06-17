package com.sbdc.sbdcweb.board.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.DongjungAllDto;
import com.sbdc.sbdcweb.board.domain.response.DongjungOneDto;
import com.sbdc.sbdcweb.board.service.BoardDongjungService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 중소기업뉴스 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Api(tags = {"1-1-3. SBDC뉴스"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardDongjungAdminRestController extends BoardRestAdminController {
	private final BoardDongjungService boardDongjungService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public BoardDongjungAdminRestController(BoardDongjungService boardDongjungService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardDongjungService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.boardDongjungService = boardDongjungService;
        this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
        this.commonUtils = commonUtils;
        this.jwtProvider = jwtProvider;
    }

	/**
     * 중소기업뉴스 게시판 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dongjung")
    public ResponseEntity<List<DongjungAllDto>> selectBoardDongjungList(HttpServletRequest request) {
		List<DongjungAllDto> boardList = boardDongjungService.selectBoardDongjungList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardDongjungService.getBbsCode(), "목록 조회", "articleKey=" + boardList.get(0).getArticleKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 중소기업뉴스 게시판 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/dongjung/{id}")
    public ResponseEntity<DongjungOneDto> selectBoardDongjung(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		DongjungOneDto board = boardDongjungService.selectBoardDongjung(id);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardDongjungService.getBbsCode(), "조회", "articleKey=" + board.getArticleKey(), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 중소기업뉴스 게시판 관리자 수정을 위한 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/dongjung/file/{id}")
    public ResponseEntity<DongjungOneDto> selectBoardUpdateFile(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	DongjungOneDto board = boardDongjungService.selectBoardUpdateFile(id);

    	if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    /**
	 * 중소기업뉴스 게시판 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController insertBoard
	 */
	@PostMapping("/dongjung")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		boardDongjungService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardDongjungService.getBbsCode(), boardDongjungService.insertBoard(boardRequest, attach, upload));
	}

	/**
	 * 중소기업뉴스 게시판 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController updateBoard
	 */
	@PatchMapping("/dongjung/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		return super.updateBoard(request, id, boardRequest, boardDongjungService.getBbsCode(), boardDongjungService.updateBoard(id, boardRequest, attach, upload));
	}

	/**
	 * 중소기업뉴스 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@DeleteMapping("/dongjung/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardDongjungService.getBbsCode(), boardDongjungService.deleteBoard(id));
	}

	/**
	 * 중소기업뉴스 게시판 관리자 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값
	 * @param 	thumb		썸네일 사용 여부
	 * @return	BoardRestController	selectImage
	 */
    @GetMapping("/dongjung/image/{attachKey}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "attachKey") Long attachKey, @RequestParam(value = "thumb", required = false) String thumb) {
    	return super.selectImage(request, attachKey, thumb);
    }

}
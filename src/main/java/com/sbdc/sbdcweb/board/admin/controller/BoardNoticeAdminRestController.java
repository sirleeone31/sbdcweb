package com.sbdc.sbdcweb.board.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sbdc.sbdcweb.board.domain.response.NoticeAllDto;
import com.sbdc.sbdcweb.board.domain.response.NoticeOneDto;
import com.sbdc.sbdcweb.board.service.BoardNoticeService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 공지사항 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Api(tags = {"1-1-8. 공지사항"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardNoticeAdminRestController extends BoardRestAdminController {
	private final BoardNoticeService boardNoticeService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public BoardNoticeAdminRestController(BoardNoticeService boardNoticeService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardNoticeService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.boardNoticeService = boardNoticeService;
        this.adminLogService = adminLogService;
        this.fileManager = fileManager;
        this.commonUtils = commonUtils;
        this.jwtProvider = jwtProvider;
    }

	/**
     * 공지사항 게시판 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/notice")
    public ResponseEntity<List<NoticeAllDto>> selectBoardNoticeList(HttpServletRequest request) {
		List<NoticeAllDto> boardList = boardNoticeService.selectBoardNoticeList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardNoticeService.getBbsCode(), "목록 조회", "articleKey=" + boardList.get(0).getArticleKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 공지사항 게시판 관리자 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/notice/{id}")
    public ResponseEntity<NoticeOneDto> selectBoardNotice(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		NoticeOneDto board = boardNoticeService.selectBoardNotice(id);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardNoticeService.getBbsCode(), "조회", "articleKey=" + board.getArticleKey(), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 공지사항 게시판 관리자 수정을 위한 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/notice/file/{id}")
    public ResponseEntity<NoticeOneDto> selectBoardUpdateFile(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	NoticeOneDto board = boardNoticeService.selectBoardUpdateFile(id);

    	if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    /**
	 * 공지사항 게시판 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController insertBoard
	 */
	@PostMapping("/notice")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		boardNoticeService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardNoticeService.getBbsCode(), boardNoticeService.insertBoard(boardRequest, attach, upload));
	}

	/**
	 * 공지사항 게시판 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController updateBoard
	 */
	@PatchMapping("/notice/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		return super.updateBoard(request, id, boardRequest, boardNoticeService.getBbsCode(), boardNoticeService.updateBoard(id, boardRequest, attach, upload));
	}

	/**
	 * 공지사항 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@DeleteMapping("/notice/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardNoticeService.getBbsCode(), boardNoticeService.deleteBoard(id));
	}

    /**
	 * 공지사항 게시판 관리자 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값
	 * @param 	thumb		썸네일 사용 여부
	 * @return	BoardRestController	selectImage
	 */
    @GetMapping("/notice/image/{attachKey}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "attachKey") Long attachKey, @RequestParam(value = "thumb", required = false) String thumb) {
    	return super.selectImage(request, attachKey, thumb);
    }

    /**
     * 공지사항 게시판 관리자 게시물 첨부파일 다운로드
     *
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
     * @param 	attachKey	ATTACH_KEY 값
	 * @return	BoardRestController downloadBoard
     */
    @GetMapping("/notice/download/{attachKey}")
    public ResponseEntity<InputStreamResource> downloadBoard(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "attachKey") Long attachKey) {
    	return super.downloadBoard(request, response, attachKey);
    }

}
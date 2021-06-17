package com.sbdc.sbdcweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.board.domain.response.NoticeAllDto;
import com.sbdc.sbdcweb.board.domain.response.NoticeOneDto;
import com.sbdc.sbdcweb.board.service.BoardNoticeService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 공지사항 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Api(tags = {"1-1-8. 공지사항"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardNoticeRestController extends BoardRestController {
	private final BoardNoticeService boardNoticeService;

	@Autowired
    public BoardNoticeRestController(BoardNoticeService boardNoticeService, FileManager fileManager, CommonUtils commonUtils) {
		super(boardNoticeService, fileManager, commonUtils);
    	this.boardNoticeService = boardNoticeService;
        this.fileManager = fileManager;
    }

	/**
     * 공지사항 게시판 전체목록 조회
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

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 공지사항 게시판 특정 게시물 조회
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

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 공지사항 게시판 수정을 위한 특정 게시물 조회
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
	 * 공지사항 게시판 이미지 불러오기
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
     * 공지사항 게시판 게시물 첨부파일 다운로드
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

	/**
     * 공지사항 게시판 메인 일부목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/notice-main")
    public ResponseEntity<List<NoticeAllDto>> selectBoardNoticeTopMainList(HttpServletRequest request) {
		List<NoticeAllDto> boardList = boardNoticeService.selectBoardNoticeTopList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
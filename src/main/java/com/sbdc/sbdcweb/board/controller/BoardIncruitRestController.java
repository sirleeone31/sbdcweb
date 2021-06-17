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

import com.sbdc.sbdcweb.board.domain.response.IncruitAllDto;
import com.sbdc.sbdcweb.board.domain.response.IncruitOneDto;
import com.sbdc.sbdcweb.board.service.BoardIncruitService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 채용안내 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Api(tags = {"1-1-6. 채용공고"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardIncruitRestController extends BoardRestController {
	private final BoardIncruitService boardIncruitService;

	@Autowired
    public BoardIncruitRestController(BoardIncruitService boardIncruitService, FileManager fileManager, CommonUtils commonUtils) {
		super(boardIncruitService, fileManager, commonUtils);
    	this.boardIncruitService = boardIncruitService;
        this.fileManager = fileManager;
    }

	/**
     * 채용안내 게시판 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/incruit")
    public ResponseEntity<List<IncruitAllDto>> selectBoardIncruitList(HttpServletRequest request) {
		List<IncruitAllDto> boardList = boardIncruitService.selectBoardIncruitList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 채용안내 게시판 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/incruit/{id}")
    public ResponseEntity<IncruitOneDto> selectBoardIncruit(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		IncruitOneDto board = boardIncruitService.selectBoardIncruit(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 채용안내 게시판 수정을 위한 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/incruit/file/{id}")
    public ResponseEntity<IncruitOneDto> selectBoardUpdateFile(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	IncruitOneDto board = boardIncruitService.selectBoardUpdateFile(id);

    	if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    /**
	 * 채용안내 게시판 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값
	 * @param 	thumb		썸네일 사용 여부
	 * @return	BoardRestController	selectImage
	 */
    @GetMapping("/incruit/image/{attachKey}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "attachKey") Long attachKey, @RequestParam(value = "thumb", required = false) String thumb) {
    	return super.selectImage(request, attachKey, thumb);
    }

    /**
     * 채용안내 게시판 게시물 첨부파일 다운로드
     *
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
     * @param 	attachKey	ATTACH_KEY 값
	 * @return	BoardRestController downloadBoard
     */
    @GetMapping("/incruit/download/{attachKey}")
    public ResponseEntity<InputStreamResource> downloadBoard(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "attachKey") Long attachKey) {
    	return super.downloadBoard(request, response, attachKey);
    }

	/**
     * 채용안내 메인 일부목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/incruit-main")
    public ResponseEntity<List<IncruitAllDto>> selectBoardIncruitTopList(HttpServletRequest request) {
		List<IncruitAllDto> boardList = boardIncruitService.selectBoardIncruitTopList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
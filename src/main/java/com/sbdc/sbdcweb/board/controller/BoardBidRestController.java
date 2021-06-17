package com.sbdc.sbdcweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.board.domain.response.BidAllDto;
import com.sbdc.sbdcweb.board.domain.response.BidOneDto;
import com.sbdc.sbdcweb.board.service.BoardBidService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 입찰정보 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Api(tags = {"1-1-1. 입찰정보"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardBidRestController extends BoardRestController {
	private final BoardBidService boardBidService;

    @Autowired
    public BoardBidRestController(BoardBidService boardBidService, FileManager fileManager, CommonUtils commonUtils) {
		super(boardBidService, fileManager, commonUtils);
    	this.boardBidService = boardBidService;
        this.fileManager = fileManager;
    }

	/**
     * 입찰정보 게시판 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@ApiOperation(value = "입찰정보 목록 조회", notes = "모든 입찰정보 목록을 조회한다.")
	@GetMapping("/bid")
    public ResponseEntity<List<BidAllDto>> selectBoardBidList(HttpServletRequest request) {
		List<BidAllDto> boardList = boardBidService.selectBoardBidList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 입찰정보 게시판 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@ApiOperation(value = "입찰정보 조회", notes = "특정 입찰정보를 조회한다.")
	@ApiImplicitParam(name = "id", value = "게시물 번호(articleNo)", required = true, paramType = "path")
	@GetMapping("/bid/{id}")
    public ResponseEntity<BidOneDto> selectBoardBid(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		BidOneDto board = boardBidService.selectBoardBid(id, role);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 입찰정보 게시판 수정을 위한 특정 게시물 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @ApiOperation(value = "입찰정보, 첨부파일 Base64 조회", notes = "입찰정보 수정페이지 로드 시 필요한 정보를 조회한다.")
    @GetMapping("/bid/file/{id}")
    public ResponseEntity<BidOneDto> selectBoardUpdateFile(HttpServletRequest request, @ApiParam(value = "articleKey", required = true) @PathVariable(value = "id") Long id) {
    	BidOneDto board = boardBidService.selectBoardUpdateFile(id);

    	if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    /**
	 * 입찰정보 게시판 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값
	 * @param 	thumb		썸네일 사용 여부
	 * @return	BoardRestController	selectImage
	 */
    @GetMapping("/bid/image/{attachKey}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "attachKey") Long attachKey, @RequestParam(value = "thumb", required = false) String thumb) {
    	return super.selectImage(request, attachKey, thumb);
    }

    /**
     * 입찰정보 게시판 게시물 첨부파일 다운로드
     *
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
     * @param 	attachKey	ATTACH_KEY 값
	 * @return	BoardRestController downloadBoard
     */
    @GetMapping("/bid/download/{attachKey}")
    public ResponseEntity<InputStreamResource> downloadBoard(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "attachKey") Long attachKey) {
    	return super.downloadBoard(request, response, attachKey);
    }

	/**
     * 입찰정보 메인 일부목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/bid-main")
    public ResponseEntity<List<BidAllDto>> selectBoardBidTopList(HttpServletRequest request) {
		List<BidAllDto> boardList = boardBidService.selectBoardBidTopList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
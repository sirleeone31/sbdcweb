package com.sbdc.sbdcweb.board.admin.controller;

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
import com.sbdc.sbdcweb.board.domain.response.BidAllDto;
import com.sbdc.sbdcweb.board.domain.response.BidOneDto;
import com.sbdc.sbdcweb.board.service.BoardBidService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 입찰정보 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Api(tags = {"1-1-1. 입찰정보"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardBidAdminRestController extends BoardRestAdminController {
	private final BoardBidService boardBidService;
    private final AdminLogService adminLogService;
    private final CommonUtils commonUtils;
    private final JwtProvider jwtProvider;

    @Autowired
    public BoardBidAdminRestController(BoardBidService boardBidService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardBidService, adminLogService, fileManager, commonUtils, jwtProvider);
    	this.boardBidService = boardBidService;
        this.adminLogService = adminLogService;
        this.fileManager = fileManager;
        this.commonUtils = commonUtils;
        this.jwtProvider = jwtProvider;
    }

	/**
     * 입찰정보 게시판 관리자 전체목록 조회
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

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardBidService.getBbsCode(), "목록 조회", "articleKey=" + boardList.get(0).getArticleKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 입찰정보 게시판 관리자 특정 게시물 조회
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

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardBidService.getBbsCode(), "조회", "articleKey=" + board.getArticleKey(), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 입찰정보 게시판 관리자 수정을 위한 특정 게시물 조회
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
	 * 입찰정보 게시판 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController insertBoard
	 */
	@ApiOperation(value = "입찰정보 생성", notes = "입찰정보를 생성한다.")
	@PostMapping("/bid")
	public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		boardBidService.insertIp(commonUtils.findIp(request), boardRequest);
		return super.insertBoard(request, boardRequest, boardBidService.getBbsCode(), boardBidService.insertBoard(boardRequest, attach, upload));
	}

	/**
	 * 입찰정보 게시판 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 attach 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	BoardRestController updateBoard
	 */
	@ApiOperation(value = "입찰정보 수정", notes = "입찰정보 내용을 수정한다.")
	@ApiImplicitParam(name = "id", value = "게시물 번호(articleNo)", required = true, paramType = "path")
	@PatchMapping("/bid/{id}")
	public ResponseEntity<Board> updateBoard(HttpServletRequest request, @PathVariable(value = "id") Long id, Board boardRequest, BoardAttach attach, @RequestPart(value = "upload", required = false) List<MultipartFile> upload) {
		return super.updateBoard(request, id, boardRequest, boardBidService.getBbsCode(), boardBidService.updateBoard(id, boardRequest, attach, upload));
	}

	/**
	 * 입찰정보 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			ARTICLE_KEY 값
	 * @return	BoardRestController deleteBoard
	 */
	@ApiOperation(value = "입찰정보 삭제", notes = "입찰정보를 삭제한다.")
	@ApiImplicitParam(name = "id", value = "게시물 번호(articleNo)", required = true, paramType = "path")
	@DeleteMapping("/bid/{id}")
	public ResponseEntity<Board> deleteBoard(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		return super.deleteBoard(request, id, boardBidService.getBbsCode(), boardBidService.deleteBoard(id));
	}

    /**
	 * 입찰정보 게시판 관리자 이미지 불러오기
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
     * 입찰정보 게시판 관리자 게시물 첨부파일 다운로드
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

}
package com.sbdc.sbdcweb.board.admin.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbdc.sbdcweb.board.service.BoardService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.SecretDomain;

/**
 * 게시판 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@RestController
@RequestMapping("/admin/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardRestAdminController extends BaseRestController<Board, Long> {
	protected BoardService boardService;
	protected AdminLogService adminLogService;
	protected FileManager fileManager;
	protected CommonUtils commonUtils;
	protected JwtProvider jwtProvider;
	protected String role = "admin";

	@Autowired
    public BoardRestAdminController(BoardService boardService, AdminLogService adminLogService, FileManager fileManager, CommonUtils commonUtils, JwtProvider jwtProvider) {
    	super(boardService);
    	this.boardService = boardService;
    	this.adminLogService = adminLogService;
    	this.fileManager = fileManager;
    	this.commonUtils = commonUtils;
    	this.jwtProvider = jwtProvider;
	}

    /**
     * 게시판 관리자 전체목록 조회
     * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	code				bbsCode 값
	 * @param 	boardListResponse	response 한 List 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    public ResponseEntity<List<Board>> selectBoardList(HttpServletRequest request, String code, List<Board> boardListResponse) {
		if (boardListResponse.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "목록 조회", "articleKey=" + boardListResponse.get(0).getArticleKey() + " 외 " + (boardListResponse.size()-1) + "개", commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardListResponse);
	}

    /**
	 * 게시판 관리자 특정 게시물 조회
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	code			bbsCode 값
	 * @param 	boardResponse	response 한 board 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<Board> selectBoard(HttpServletRequest request, Long id, String code, Board boardResponse) {
		if (boardResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "조회", "articleKey=" + boardResponse.getArticleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).body(boardResponse);
	}

    /**
	 * 게시판 관리자 게시물 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	code			bbsCode 값
	 * @param 	boardResponse	response 한 board 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<Board> insertBoard(HttpServletRequest request, Board boardRequest, String code, Board boardResponse) {
		if (boardResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "입력", "articleKey=" + boardResponse.getArticleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

    /**
	 * 게시판 관리자 게시물 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	code			bbsCode 값
	 * @param 	boardResponse	response 한 board 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<Board> updateBoard(HttpServletRequest request, Long id, Board boardRequest, String code, Board boardResponse) {
		if (boardResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "수정", "articleKey=" + boardResponse.getArticleKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
	 * 게시판 관리자 게시물 삭제
	 * 
	 * @param 	request				HttpServletRequest 객체
	 * @param 	id					ARTICLE_KEY 값 
	 * @param 	code				bbsCode 값
	 * @param 	boardMapResponse	response 한 board 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<Board> deleteBoard(HttpServletRequest request, Long id, String code, Map<String,Object> boardMapResponse) {
    	boolean deleteYN = (boolean) boardMapResponse.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				code, "삭제", "articleKey=" + boardMapResponse.get("articleKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
	 * 게시판 관리자 첨부파일 다운로드
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	response	HttpServletResponse 객체
	 * @param 	id			ATTACH_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<InputStreamResource> downloadBoard(HttpServletRequest request, HttpServletResponse response, Long attachKey) {
        Map<String, Object> boardMap = boardService.downloadBoard(attachKey);
    	boolean downloadYN = (boolean) boardMap.get("downloadYN");
    	boolean successYN = false;

    	if (downloadYN) {
    		successYN = fileManager.doFileDownload((String) boardMap.get("saveFilename"), (String) boardMap.get("originalFilename"), (String) boardMap.get("pathName"), request, response);
    	}

    	if (!successYN) {
    		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    	} else {
    		return ResponseEntity.status(HttpStatus.OK).build();
    	}
    }

    /**
	 * 게시판 관리자 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	attachKey	ATTACH_KEY 값 
     * @param 	thumb 		썸네일 사용 여부
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, Long attachKey, String thumb) {
    	String mediaType = null;
    	Map<String, Object> imageMap = boardService.selectImage(attachKey, thumb);

    	if(imageMap.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}

    	File file = (File)imageMap.get("file");
    	String fileEXT = (String)imageMap.get("fileEXT");
    	InputStreamResource inputStreamResource = (InputStreamResource)imageMap.get("inputStreamResource");

    	if (fileEXT.equalsIgnoreCase("png")) {
        	mediaType = "image/png";
		} else if (fileEXT.equalsIgnoreCase("bmp")) {
        	mediaType = "image/bmp";
		} else if (fileEXT.equalsIgnoreCase("gif")) {
        	mediaType = "image/gif";
		} else {
        	mediaType = "image/jpeg";
		}

    	return ResponseEntity.status(HttpStatus.OK)
    			.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + file.getName() + "\"")
    			.header(HttpHeaders.CONTENT_TYPE, mediaType)
    			.contentLength(file.length())
    			.body(inputStreamResource);
    }

    /**
	 * 게시판 관리자 회원 비밀글 조회
     *
	 * @param 	request		HttpServletRequest 객체
     * @param 	id 			ARTICLE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    public ResponseEntity<SecretDomain> secretArticle(HttpServletRequest request, Long id) {
		SecretDomain secretDomain = boardService.secretArticle(id);

		if (secretDomain == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(secretDomain);
    }

}
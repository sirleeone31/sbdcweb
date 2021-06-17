package com.sbdc.sbdcweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.sbdc.sbdcweb.board.domain.response.DongjungAllDto;
import com.sbdc.sbdcweb.board.domain.response.DongjungOneDto;
import com.sbdc.sbdcweb.board.service.BoardDongjungService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;

/**
 * 중소기업뉴스 게시판 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Api(tags = {"1-1-3. SBDC뉴스"})
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardDongjungRestController extends BoardRestController {
	private final BoardDongjungService boardDongjungService;

	@Autowired
    public BoardDongjungRestController(BoardDongjungService boardDongjungService, FileManager fileManager, CommonUtils commonUtils) {
		super(boardDongjungService, fileManager, commonUtils);
    	this.boardDongjungService = boardDongjungService;
    	this.fileManager = fileManager;
    }

	/**
     * 중소기업뉴스 게시판 전체목록 조회
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

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 중소기업뉴스 게시판 특정 게시물 조회
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

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

	/**
	 * 중소기업뉴스 게시판 수정을 위한 특정 게시물 조회
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
	 * 중소기업뉴스 게시판 이미지 불러오기
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

	/**
     * 중소기업뉴스 게시판 메인 일부목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dongjung-main")
    public ResponseEntity<List<DongjungAllDto>> selectBoardDongjungTopMainList(HttpServletRequest request) {
		List<DongjungAllDto> boardList = boardDongjungService.selectBoardDongjungTopList("main");

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
     * 중소기업뉴스 게시판 상단 일부목록 조회(웹)
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dongjung-wtop")
    public ResponseEntity<List<DongjungAllDto>> selectBoardDongjungTopWebList(HttpServletRequest request) {
		List<DongjungAllDto> boardList = boardDongjungService.selectBoardDongjungTopList("wtop");

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
     * 중소기업뉴스 게시판 상단 일부목록 조회(모바일)
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/dongjung-mtop")
    public ResponseEntity<List<DongjungAllDto>> selectBoardDongjungTopMobileList(HttpServletRequest request) {
		List<DongjungAllDto> boardList = boardDongjungService.selectBoardDongjungTopList("mtop");

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

}
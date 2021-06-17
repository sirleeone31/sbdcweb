package com.sbdc.sbdcweb.board.admin.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.admin.service.AdminLogService;
import com.sbdc.sbdcweb.board.domain.BoardContractType;
import com.sbdc.sbdcweb.board.service.BoardContractTypeService;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;

/**
 * 입찰정보 카테고리 관리자 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
@Api(tags = {"1-2-3. 입찰정보 카테고리"})
@RestController
@RequestMapping("/admin/board")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BoardContractTypeAdminRestController extends BaseRestController<BoardContractType, Long> {
    private final BoardContractTypeService boardContractTypeService;
	private final AdminLogService adminLogService;
	private final CommonUtils commonUtils;
	private final JwtProvider jwtProvider;

	@Autowired
    public BoardContractTypeAdminRestController(BoardContractTypeService boardContractTypeService, AdminLogService adminLogService, CommonUtils commonUtils, JwtProvider jwtProvider) {
		super(boardContractTypeService);
		this.boardContractTypeService = boardContractTypeService;
		this.adminLogService = adminLogService;
		this.commonUtils = commonUtils;
		this.jwtProvider = jwtProvider;
    }

	/**
     * 입찰정보 카테고리 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/contract-type")
    public ResponseEntity<List<BoardContractType>> selectList(HttpServletRequest request) {
		List<BoardContractType> boardList = boardContractTypeService.selectList();

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
        adminLogService.insertAdminLog(
                jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
        		boardContractTypeService.getBbsCode(), "목록 조회", "typeKey=" + boardList.get(0).getTypeKey() + " 외 " + (boardList.size()-1) + "개", commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}

	/**
	 * 입찰정보 카테고리 관리자 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			TYPE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/contract-type/{id}")
	public ResponseEntity<BoardContractType> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	BoardContractType board = boardContractTypeService.select(id);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardContractTypeService.getBbsCode(), "조회", "typeKey=" + board.getTypeKey(), commonUtils.findIp(request));

        return ResponseEntity.status(HttpStatus.OK).body(board);
	}

    /**
	 * 입찰정보 카테고리 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	boardRequest	Front에서 입력된 ContractType 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/contract-type")
	public ResponseEntity<BoardContractType> insert(HttpServletRequest request, @RequestBody BoardContractType boardRequest) {
    	BoardContractType board = boardContractTypeService.insert(boardRequest);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardContractTypeService.getBbsCode(), "입력", "typeKey=" + board.getTypeKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

	/**
	 * 입찰정보 카테고리 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				TYPE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 ContractType 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/contract-type/{id}")
	public ResponseEntity<BoardContractType> update(HttpServletRequest request, @PathVariable(value = "id") Long id, @RequestBody BoardContractType boardRequest) {
    	BoardContractType board = boardContractTypeService.update(id, boardRequest);

		if (board == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardContractTypeService.getBbsCode(), "수정", "typeKey=" + board.getTypeKey(), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
    }

	/**
	 * 입찰정보 카테고리 관리자 삭제
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				TYPE_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/contract-type/{id}")
	public ResponseEntity<BoardContractType> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Map<String, Object> boardMap = boardContractTypeService.delete(id);
    	boolean deleteYN = (boolean) boardMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		// LOG 처리
		adminLogService.insertAdminLog(
		        jwtProvider.getUserNameFromJwtToken(jwtProvider.getHeaderJwt(request.getHeader("Authorization"))), 
				boardContractTypeService.getBbsCode(), "삭제", "typeKey=" + boardMap.get("typeKey"), commonUtils.findIp(request));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
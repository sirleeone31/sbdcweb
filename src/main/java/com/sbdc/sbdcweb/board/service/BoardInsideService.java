package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.response.InsideAllDto;
import com.sbdc.sbdcweb.board.domain.response.InsideOneDto;

/**
 * 내부신고센터 게시판 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
public interface BoardInsideService extends BoardService {
	public List<InsideAllDto> selectBoardInsideList();
	public InsideOneDto selectBoardInside(Long id, String role);
	public Board update(Long id, Board boardRequest, String role);
}
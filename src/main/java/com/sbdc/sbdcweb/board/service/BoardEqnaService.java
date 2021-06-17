package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.response.EqnaAllDto;
import com.sbdc.sbdcweb.board.domain.response.EqnaOneDto;
import com.sbdc.sbdcweb.board.service.BoardService;

/**
 * 윤리경영 QNA 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
public interface BoardEqnaService extends BoardService {
	public List<EqnaAllDto> selectBoardEqnaList();
	public EqnaOneDto selectBoardEqna(Long id, String role);
	public Board update(Long id, Board boardRequest, String role, Long memberKey);
}
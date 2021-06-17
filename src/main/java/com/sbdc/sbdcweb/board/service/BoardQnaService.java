package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.response.QnaAllDto;
import com.sbdc.sbdcweb.board.domain.response.QnaOneDto;

/**
 * 고객상담 게시판 Service
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
public interface BoardQnaService extends BoardService {
	public List<QnaAllDto> selectBoardQnaList();
	public QnaOneDto selectBoardQna(Long id, String role);
	public Board update(Long id, Board boardRequest, String role, Long memberKey);
}
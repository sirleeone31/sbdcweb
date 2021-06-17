package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.response.ConsultAllDto;
import com.sbdc.sbdcweb.board.domain.response.ConsultOneDto;

/**
 * 입점 및 판매상담 게시판  Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
public interface BoardConsultService extends BoardService {
	public List<ConsultAllDto> selectBoardConsultList();
	public ConsultOneDto selectBoardConsult(Long id, String role);
	public Board update(Long id, Board boardRequest, String role, Long memberKey);
}
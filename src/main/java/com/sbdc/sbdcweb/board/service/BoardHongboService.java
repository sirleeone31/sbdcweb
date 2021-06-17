package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.HongboAllDto;
import com.sbdc.sbdcweb.board.domain.response.HongboOneDto;

/**
 * 우리제품홍보하기 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
public interface BoardHongboService extends BoardService {
    public List<HongboAllDto> selectBoardHongboList();
	public HongboOneDto selectBoardHongbo(Long id);
	public HongboOneDto selectBoardUpdateFile(Long id);

}
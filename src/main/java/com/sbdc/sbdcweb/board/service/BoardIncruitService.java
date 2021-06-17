package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.IncruitAllDto;
import com.sbdc.sbdcweb.board.domain.response.IncruitOneDto;

/**
 * 채용안내 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
public interface BoardIncruitService extends BoardService {
    public List<IncruitAllDto> selectBoardIncruitList();
	public IncruitOneDto selectBoardIncruit(Long id, String role);
	public IncruitOneDto selectBoardUpdateFile(Long id);
	public List<IncruitAllDto> selectBoardIncruitTopList();

}
package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.DongjungAllDto;
import com.sbdc.sbdcweb.board.domain.response.DongjungOneDto;

/**
 * 중소기업뉴스 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
public interface BoardDongjungService extends BoardService {
    public List<DongjungAllDto> selectBoardDongjungList();
	public DongjungOneDto selectBoardDongjung(Long id);
	public DongjungOneDto selectBoardUpdateFile(Long id);
	public List<DongjungAllDto> selectBoardDongjungTopList(String view);

}
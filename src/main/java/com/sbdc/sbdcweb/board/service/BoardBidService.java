package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.BidAllDto;
import com.sbdc.sbdcweb.board.domain.response.BidOneDto;

/**
 * 입찰정보 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-29
 */
public interface BoardBidService extends BoardService {
    public List<BidAllDto> selectBoardBidList();
	public BidOneDto selectBoardBid(Long id, String role);
	public BidOneDto selectBoardUpdateFile(Long id);
    public List<BidAllDto> selectBoardBidTopList();

}
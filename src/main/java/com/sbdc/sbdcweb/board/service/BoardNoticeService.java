package com.sbdc.sbdcweb.board.service;

import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.NoticeAllDto;
import com.sbdc.sbdcweb.board.domain.response.NoticeOneDto;

/**
 * 공지사항 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
public interface BoardNoticeService extends BoardService {
    public List<NoticeAllDto> selectBoardNoticeList();
	public NoticeOneDto selectBoardNotice(Long id);
	public NoticeOneDto selectBoardUpdateFile(Long id);
	public List<NoticeAllDto> selectBoardNoticeTopList();

}
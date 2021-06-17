package com.sbdc.sbdcweb.board.attach.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.AttachAllDto;

/**
 * 첨부파일 Service
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-29
 */
public interface BoardAttachService {
	public List<BoardAttach> selectBoardAttachList();
	public List<BoardAttach> selectBoardAttachList(Long articleKey);
	public BoardAttach selectBoardAttach(Long id);
	public BoardAttach selectBoardAttachByArticleKey(Long articleKey);

	public BoardAttach insertBoardAttach(BoardAttach boardAttachRequest, MultipartFile upload, String bbsCode, Long articleKey);

	public Map<String, Object> deleteBoardAttach(Long id);
	public Map<String, Object> deleteBoardAttachByArticleKey(Long articleKey);

	public List<AttachAllDto> selectBoardAttachListMain();
	public Long maxAttachKey();

}
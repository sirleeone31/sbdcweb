package com.sbdc.sbdcweb.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.SecretDomain;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 게시판 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
public interface BoardService extends BaseService<Board, Long> {
	public String getBoardCode();
	public void setBoardCode(String boardCode);
	public String getBbsCode();
	public void setBbsCode(String bbsCode);
	public String getPathName();
	public void setPathName(String pathName);

	public List<Board> selectBoardList();
	public Board insertBoard(Board boardRequest);
	public Board insertBoard(Board boardRequest, BoardAttach attach, List<MultipartFile> upload);
	public Board updateBoard(Long id, Board boardRequest, BoardAttach attach, List<MultipartFile> upload);
	public Map<String, Object> deleteBoard(Long id);
	public Map<String, Object> delete(Long id, Long memberKey);

	public Map<String, Object> downloadBoard(Long attachKey);
	public Map<String, Object> selectImage(Long attachKey, String thumb);

	public Long maxArticleNo();
	public SecretDomain secretArticle(Long id);
	public void insertIp(String ip, Board boardRequest);

}
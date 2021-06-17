package com.sbdc.sbdcweb.board.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.NoticeAllDto;
import com.sbdc.sbdcweb.board.domain.response.NoticeOneDto;
import com.sbdc.sbdcweb.board.repository.BoardRepository;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;

/**
 * 공지사항 게시판 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Service
public class BoardNoticeServiceImpl extends BoardServiceImpl implements BoardNoticeService {
	private static final Logger logger = LoggerFactory.getLogger(BoardNoticeServiceImpl.class);

    private final BoardAttachService boardAttachService;
    private final String boardCode = "bbs";
    private final String bbsCode = "notice";
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + boardCode + File.separator + bbsCode;

    @Autowired
	public BoardNoticeServiceImpl(BoardRepository boardRepository, BoardAttachService boardAttachService, CommonUtils commonUtils, FileManager fileManager, ImageManager imageManager) {
		super(boardRepository, boardAttachService, commonUtils, fileManager, imageManager);
		super.setBoardCode(boardCode);
		super.setBbsCode(bbsCode);
        super.setPathName(pathName);
		this.boardRepository = boardRepository;
		this.boardAttachService = boardAttachService;
		this.commonUtils = commonUtils;
    }

	/**
     * 공지사항 게시판 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	@Override
	public List<NoticeAllDto> selectBoardNoticeList()  {
		List<NoticeAllDto> boardNoticeList = new ArrayList<NoticeAllDto>();
		List<Board> boardList = super.selectBoardList();

		for (Board board : boardList) {
			boardNoticeList.add(new NoticeAllDto(
		    		 board.getArticleKey(),
		    		 board.getArticleNo(),
		    		 board.getSubject(),
		    		 board.getWriter(),
		    		 board.getMemberKey(),
		    		 board.getRegDate()));
		}

		return boardNoticeList;
	}

	/**
	 * 공지사항 게시판 특정 게시물 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 board 자료
	 */
	@Override
	public NoticeOneDto selectBoardNotice(Long id) {
		Board board = super.select(id);
		NoticeOneDto boardNotice = null;
		List<String> file = new ArrayList<String>();

		if (board != null) {
		     boardNotice = new NoticeOneDto(
					board.getArticleKey(),
					board.getArticleNo(),
					board.getSubject(),
					board.getContent1(),
					board.getWriter(),
					board.getMemberKey(),
					board.getRegDate());

		     boardNotice.setFile(file);
		     boardNotice.setBoardAttach(boardAttachService.selectBoardAttachList(id));

		     boardRepository.updateHitByArticleKey(id);
		}

		return boardNotice;
	}

	/**
     * 공지사항 게시판 수정을 위한 특정 게시물 조회
     * 
     * 1차 수정 : 김도현
     * 2차 수정 : 김도현
	 * @param 	id		ARTICLE_KEY 값
     * @return 	파일 base64 데이터 포함한 데이터 return
     */
	@Override
	public NoticeOneDto selectBoardUpdateFile(Long id) {
		Board board = super.select(id);
		NoticeOneDto boardNotice = null;
		List<String> fileAll = new ArrayList<String>();

		try {
			if (board != null) {
				boardNotice = new NoticeOneDto(
						board.getArticleKey(),
						board.getBbsCode(),
						board.getArticleNo(),
						board.getSubject(),
						board.getContent1(),
						board.getWriter(),
						board.getMemberKey(),
						board.getRegDate());

				List<BoardAttach> fileList = boardAttachService.selectBoardAttachList(id);

				for (BoardAttach fileOne : fileList) {
					String fileString;
					FileInputStream inputStream = null;
					ByteArrayOutputStream byteOutStream = null;
					File file = new File(pathName + File.separator + fileOne.getGuidName());

					inputStream = new FileInputStream(file);
					byteOutStream = new ByteArrayOutputStream();

					int len = 0;
					byte[] buf = new byte[1024];
					while ((len = inputStream.read(buf)) != -1) {
						byteOutStream.write(buf, 0, len);
					}

					byte[] fileArray = byteOutStream.toByteArray();
					fileString = new String(Base64.encodeBase64(fileArray));

					fileAll.add(fileString);

					boardNotice.setFile(fileAll);
					boardNotice.setBoardAttach(fileList);

					inputStream.close();
					byteOutStream.close();
				}
			}
		} catch (IOException e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러1 - IO", e.getMessage(), e);
		} catch (Exception e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러2 - 기타", e.getMessage(), e);
		}
        return boardNotice;
    }

	/**
     * 공지사항 게시판 게시물 수정
     * 수정 : 김도현
     * 제목 및 내용 수정 가능 / 첨부파일은 수정이 아닌 추가 or 삭제개념으로 처리한다.
     *** TB_BBS_CONTENTIMAGE(이미지) 관련 처리 필요함 ***
     * 
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
     * @param 	attach   		첨부파일 관련 데이터
     * @param 	upload   		Front에서 입력받은 파일 객체
	 * @return	수정 로직이 적용된 board 자료
     */
	@Override
    public Board updateBoard(Long id, Board boardRequest, BoardAttach attach, List<MultipartFile> upload) {
        Board board = super.select(id);

		if (board != null) {
			if (boardRequest.getSubject() != null) {
				board.setSubject(boardRequest.getSubject());
			}

			if (boardRequest.getContent1() != null) {
				board.setContent1(boardRequest.getContent1());
			}

		}
		return super.updateBoard(id, board, attach, upload);
    }

	/**
     * 공지사항 메인 일부목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	public List<NoticeAllDto> selectBoardNoticeTopList() {
		List<NoticeAllDto> boardBidList = new ArrayList<NoticeAllDto>();
		List<Board> boardList = null;

		boardList = boardRepository.findTopByBbsCode(bbsCode, PageRequest.of(0, 3));

		for (Board board : boardList) {
			boardBidList.add(new NoticeAllDto(
		    board.getArticleKey(),
		    board.getSubject(),
		    board.getRegDate()));
		}

		return boardBidList;
    }

}
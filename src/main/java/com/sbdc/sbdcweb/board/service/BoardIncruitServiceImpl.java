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
import com.sbdc.sbdcweb.board.domain.response.IncruitAllDto;
import com.sbdc.sbdcweb.board.domain.response.IncruitOneDto;
import com.sbdc.sbdcweb.board.repository.BoardRepository;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;

/**
 * 채용안내 게시판 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-03
 */
@Service
public class BoardIncruitServiceImpl extends BoardServiceImpl implements BoardIncruitService {
	private static final Logger logger = LoggerFactory.getLogger(BoardIncruitServiceImpl.class);

    private final BoardAttachService boardAttachService;
    private final CommonUtils commonUtils;
    private final String boardCode = "bbs";
    private final String bbsCode = "incruit";
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + boardCode + File.separator + bbsCode;

    @Autowired
	public BoardIncruitServiceImpl(BoardRepository boardRepository, BoardAttachService boardAttachService, CommonUtils commonUtils, FileManager fileManager, ImageManager imageManager) {
		super(boardRepository, boardAttachService, commonUtils, fileManager, imageManager);
		super.setBoardCode(boardCode);
		super.setBbsCode(bbsCode);
        super.setPathName(pathName);
		this.boardRepository = boardRepository;
		this.boardAttachService = boardAttachService;
		this.commonUtils = commonUtils;
	}

	/**
     * 채용안내 게시판 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	@Override
	public List<IncruitAllDto> selectBoardIncruitList() {
		List<IncruitAllDto> boardIncruitList = new ArrayList<IncruitAllDto>();
		List<Board> boardList = super.selectBoardList();

		for (Board board : boardList) {
		     if (Long.parseLong(commonUtils.nowDateFormatter()) > Long.parseLong(board.getEndDate())) {
		    	 board.setComplete("Y");
		     } else {
		    	 board.setComplete("N");
		     }

		     boardIncruitList.add(new IncruitAllDto(
		    		 board.getArticleKey(),
		    		 board.getArticleNo(),
		    		 board.getSubject(),
		    		 board.getRegDate(),
		    		 board.getEndDate(),
		    		 board.getComplete()));
		}

		return boardIncruitList;
	}

	/**
	 * 채용안내 게시판 특정 게시물 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 board 자료
	 */
	@Override
	public IncruitOneDto selectBoardIncruit(Long id, String role) {
		Board board = super.select(id);
		IncruitOneDto boardIncruit = null;
		List<String> file = new ArrayList<String>();

		if (board != null) {
		     if (Long.parseLong(commonUtils.nowDateFormatter()) > Long.parseLong(board.getEndDate())) {
		    	 board.setComplete("Y");
		     } else {
		    	 board.setComplete("N");
		     }

		     boardIncruit = new IncruitOneDto(
					board.getArticleKey(),
					board.getArticleNo(),
					board.getSubject(),
					board.getContent1(),
					board.getWriter(),
					board.getRegDate(),
		    		board.getEndDate(),
		    		board.getComplete());

		     boardIncruit.setFile(file);
		     boardIncruit.setBoardAttach(boardAttachService.selectBoardAttachList(id));

		     if (board.getComplete().equals("Y") && role.equals("user")) {
		    	 boardIncruit.setContent1("마감된 채용공고 입니다.");
		     }

		     boardRepository.updateHitByArticleKey(id);
		}

		return boardIncruit;
	}

	/**
     * 채용안내 게시판 수정을 위한 특정 게시물 조회
     * 
     * 1차 수정 : 김도현
     * 2차 수정 : 김도현
	 * @param 	id		ARTICLE_KEY 값
     * @return 	파일 base64 데이터 포함한 데이터 return
     */
	@Override
	public IncruitOneDto selectBoardUpdateFile(Long id) {
		Board board = super.select(id);
		IncruitOneDto boardIncruit = null;
		List<String> fileAll = new ArrayList<String>();

		try {
			if (board != null) {
				boardIncruit = new IncruitOneDto(
						board.getArticleKey(),
						board.getBbsCode(),
						board.getArticleNo(),
						board.getSubject(),
						board.getContent1(),
						board.getWriter(),
						board.getRegDate(),
						board.getEndDate());

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

					boardIncruit.setFile(fileAll);
					boardIncruit.setBoardAttach(fileList);

					inputStream.close();
					byteOutStream.close();
				}
			}
		} catch (IOException e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러1 - IO", e.getMessage(), e);
		} catch (Exception e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러2 - 기타", e.getMessage(), e);
		}
        return boardIncruit;
    }

	/**
	 * 채용안내 게시판 게시물 입력
     * 수정 : 김도현
     *** TB_BBS_CONTENTIMAGE(이미지) 관련 처리 필요함 ***
     * 
	 * @param 	boardRequest	Front에서 입력된 board 자료
     * @param 	attach   		첨부파일 관련 데이터 
     * @param 	upload   		Front에서 입력받은 파일 객체
	 * @return	입력 로직이 적용된 board 자료
	 */
	@Override
    public Board insertBoard(Board boardRequest, BoardAttach attach, List<MultipartFile> upload) {
		if (boardRequest.getEndDate() != null) {
			boardRequest.setEndDate(commonUtils.frontEndDateFormatter(boardRequest.getEndDate()));
		}
		return super.insertBoard(boardRequest, attach, upload);
	}

	/**
     * 채용안내 게시판 게시물 수정
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
			if (boardRequest.getSubject() != null && !boardRequest.getSubject().equals("")) {
				board.setSubject(boardRequest.getSubject());
			}

			if (boardRequest.getContent1() != null && !boardRequest.getContent1().equals("")) {
				board.setContent1(boardRequest.getContent1());
			}

			if (boardRequest.getEndDate() != null && !boardRequest.getEndDate().equals("")) {
				board.setEndDate(commonUtils.frontEndDateFormatter(boardRequest.getEndDate()));
			}

		}

		return super.updateBoard(id, board, attach, upload);
	}

	/**
     * 채용안내 메인 일부목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	public List<IncruitAllDto> selectBoardIncruitTopList() {
		List<IncruitAllDto> boardIncruitList = new ArrayList<IncruitAllDto>();
		List<Board> boardList = boardRepository.findTopByBbsCode(bbsCode, PageRequest.of(0, 3));

		for (Board board : boardList) {
			boardIncruitList.add(new IncruitAllDto(
		    board.getArticleKey(),
		    board.getSubject(),
		    board.getRegDate()));
		}

		return boardIncruitList;
    }

}
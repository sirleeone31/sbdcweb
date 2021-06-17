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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.HongboAllDto;
import com.sbdc.sbdcweb.board.domain.response.HongboOneDto;
import com.sbdc.sbdcweb.board.repository.BoardRepository;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;

/**
 * 우리제품홍보하기 게시판 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-04
 */
@Service
public class BoardHongboServiceImpl extends BoardServiceImpl implements BoardHongboService {
	private static final Logger logger = LoggerFactory.getLogger(BoardHongboServiceImpl.class);

    private final BoardAttachService boardAttachService;
    private final String boardCode = "bbs";
    private final String bbsCode = "hongbo";
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + boardCode + File.separator + bbsCode;

    @Autowired
	public BoardHongboServiceImpl(BoardRepository boardRepository, BoardAttachService boardAttachService, CommonUtils commonUtils, FileManager fileManager, ImageManager imageManager) {
		super(boardRepository, boardAttachService, commonUtils, fileManager, imageManager);
		super.setBoardCode(boardCode);
		super.setBbsCode(bbsCode);
        super.setPathName(pathName);
		this.boardRepository = boardRepository;
		this.boardAttachService = boardAttachService;
		this.commonUtils = commonUtils;
	}

	/**
     * 우리제품홍보하기 게시판 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	@Override
	public List<HongboAllDto> selectBoardHongboList() {
		List<HongboAllDto> boardHongboList = new ArrayList<HongboAllDto>();
		List<Board> boardList = super.selectBoardList();
		Long attchKey = 1L;

		for (Board board : boardList) {
			BoardAttach boardAttach = boardAttachService.selectBoardAttachByArticleKey(board.getArticleKey());

			if (boardAttach != null) {
				attchKey = boardAttach.getAttachKey();
			} else if (boardAttach == null) {
				attchKey = 1L;				
			}

			boardHongboList.add(new HongboAllDto(
					board.getArticleKey(),
					board.getArticleNo(),
					board.getSubject(),
					board.getWriter(),
					board.getMemberKey(),
					board.getRegDate(),
					attchKey));
		}

		return boardHongboList;
	}

	/**
	 * 우리제품홍보하기 게시판 특정 게시물 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 board 자료
	 */
	@Override
	public HongboOneDto selectBoardHongbo(Long id) {
		Board board = super.select(id);
		HongboOneDto boardHongbo = null;
		List<String> file = new ArrayList<String>();

		if (board != null) {
			boardHongbo = new HongboOneDto(
					board.getArticleKey(),
					board.getArticleNo(),
					board.getSubject(),
					board.getContent1(),
					board.getWriter(),
					board.getMemberKey(),
					board.getRegDate(),
					board.getSpare1(),
					board.getSpare2(),
					board.getSpare3(),
					board.getSpare4());

			boardHongbo.setFile(file);
			boardHongbo.setBoardAttach(boardAttachService.selectBoardAttachList(id));

			boardRepository.updateHitByArticleKey(id);
		}

		return boardHongbo;
	}

	/**
     * 우리제품홍보하기 게시판 수정을 위한 특정 게시물 조회
     * 
     * 1차 수정 : 김도현
     * 2차 수정 : 김도현
	 * @param 	id		ARTICLE_KEY 값
     * @return 	파일 base64 데이터 포함한 데이터 return
     */
	@Override
	public HongboOneDto selectBoardUpdateFile(Long id) {
		Board board = super.select(id);
		HongboOneDto boardHongbo = null;
		List<String> fileAll = new ArrayList<String>();

		try {
			if (board != null) {
				boardHongbo = new HongboOneDto(
						board.getArticleKey(),
						board.getBbsCode(),
						board.getArticleNo(),
						board.getSubject(),
						board.getContent1(),
						board.getWriter(),
						board.getMemberKey(),
						board.getRegDate(),
						board.getSpare1(),
						board.getSpare2(),
						board.getSpare3(),
						board.getSpare4());

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

					boardHongbo.setFile(fileAll);
					boardHongbo.setBoardAttach(fileList);

					inputStream.close();
					byteOutStream.close();
				}
			}
		} catch (IOException e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러1 - IO", e.getMessage(), e);
		} catch (Exception e) {
            logger.error(bbsCode + " 게시판 수정을 위한 특정 게시물 조회 에러2 - 기타", e.getMessage(), e);
		}
        return boardHongbo;
    }

	/**
     * 우리제품홍보하기 게시판 게시물 수정
     * 수정 : 김도현
     * 제목 및 내용 수정 가능 / 첨부파일은 수정이 아닌 추가 or 삭제개념으로 처리한다.
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

			// Spare1 : 업체명
			if (boardRequest.getSpare1() != null && !boardRequest.getSpare1().equals("")) {
				board.setContent1(boardRequest.getSpare1());
			}
			// Spare2 : 대표전화
			if (boardRequest.getSpare2() != null && !boardRequest.getSpare2().equals("")) {
				board.setContent1(boardRequest.getSpare2());
			}
			// Spare3 : 제품문의처
			if (boardRequest.getSpare3() != null && !boardRequest.getSpare3().equals("")) {
				board.setContent1(boardRequest.getSpare3());
			}
			// Spare4 : 판매가
			if (boardRequest.getSpare4() != null && !boardRequest.getSpare4().equals("")) {
				board.setContent1(boardRequest.getSpare4());
			}

		}

		return super.updateBoard(id, board, attach, upload);
    }

}
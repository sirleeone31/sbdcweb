package com.sbdc.sbdcweb.board.service;

import java.util.ArrayList;
import java.util.List;

import com.sbdc.sbdcweb.board.domain.response.QnaAllDto;
import com.sbdc.sbdcweb.board.domain.response.QnaOneDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.repository.BoardRepository;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;
import com.sbdc.sbdcweb.mail.service.MailingService;

/**
 * 고객상담 게시판 ServiceImpl
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-27
 */
@Service
public class BoardQnaServiceImpl extends BoardServiceImpl implements BoardQnaService {
	private static final Logger logger = LoggerFactory.getLogger(BoardQnaServiceImpl.class);

	private final MailingService mailingService;
	private final String boardCode = "bbs";
	private final String bbsCode = "qna";

    @Autowired
    public BoardQnaServiceImpl(BoardRepository boardRepository, BoardAttachService boardAttachService, CommonUtils commonUtils, FileManager fileManager, ImageManager imageManager, MailingService mailingService) {
		super(boardRepository, boardAttachService, commonUtils, fileManager, imageManager);
		super.setBoardCode(boardCode);
		super.setBbsCode(bbsCode);
		this.boardRepository = boardRepository;
		this.boardAttachService = boardAttachService;
		this.commonUtils = commonUtils;
		this.mailingService = mailingService;
    }

    /**
     * 고객상담 게시판 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
	@Override
	public List<QnaAllDto> selectBoardQnaList() {
		List<QnaAllDto> boardQnaList = new ArrayList<QnaAllDto>();
		List<Board> boardList = super.selectBoardList();

		for (Board board : boardList) {
		     if (board.getContent2() == null) {
		    	 board.setComplete("N");
		     } else {
		    	 board.setComplete("Y");
		     }

		     boardQnaList.add(new QnaAllDto(
		    		 board.getArticleKey(),
		    		 board.getArticleNo(),
		    		 board.getSubject(),
		    		 board.getWriter(),
		    		 board.getMemberKey(),
		    		 board.getRegDate(),
		    		 board.getSecret(),
		    		 board.getComplete()));
		}

		return boardQnaList;
	}

	/**
	 * 고객상담 게시판 특정 게시물 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 board 자료
	 */
	@Override
	public QnaOneDto selectBoardQna(Long id, String role) {
		Board board = super.select(id);
		QnaOneDto boardQna = null;

		if (board != null) {
			boardQna = new QnaOneDto(
					board.getArticleKey(),
					board.getArticleNo(),
					board.getSubject(),
					board.getContent1(),
					board.getContent2(),
					board.getWriter(),
					board.getMemberKey(),
					board.getRegDate());

			if ((boardQna.getContent2() == null || boardQna.getContent2().equals("")) && role.equals("user")) {
				boardQna.setContent2("담당자 확인후 답변드리겠습니다.");
			}

			boardRepository.updateHitByArticleKey(id);
		}

		return boardQna;
	}

	/**
	 * 고객상담 게시판 게시물 입력
	 * 
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	입력 로직이 적용된 board 자료
	 */
	@Override
	public Board insertBoard(Board boardRequest) {
    	Board board = null;

    	try {
    		boardRequest.setBbsCode(bbsCode);
    		boardRequest.setArticleNo(maxArticleNo());

    		String maxRegDate = boardRepository.selectMaxRegDateByBbsCodeAndMemberKey(boardRequest.getBbsCode(), boardRequest.getMemberKey());

    		if (maxRegDate != null) {
        		Long diff = commonUtils.diffRegDate(maxRegDate);

        		// 사용자 60초 이내 동일 계정으로 글 작성 시 작성안됨(자동화 공격 조치) 
        		if (commonUtils.regDateTimeOut() > diff) {
        			throw new Exception();
        		}

    		}

    		board = boardRepository.save(boardRequest);

	        // 담당자 메일 발송
    		mailingService.sendMail(bbsCode, boardRequest.getWriter(), boardRequest.getSubject());
    	} catch (Exception e) {
            logger.error(bbsCode + " 게시판 게시물 입력 에러", e.getMessage(), e);
		}

        return board;
	}

	/**
	 * 고객상담 게시판 게시물 수정
	 * 
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @return	게시판 ServiceImpl updateBoard
	 */
	@Override
	public Board update(Long id, Board boardRequest, String role, Long memberKey) {
		Board board = super.select(id);

    	try {
    		if (board != null) {
				if (role.equals("admin")) {
					if (boardRequest.getContent2() != null && !boardRequest.getContent2().equals("")) {
						board.setContent2(boardRequest.getContent2());
					}
				} else if (role.equals("user")) {
    				if (!memberKey.equals(board.getMemberKey())) {
    					throw new Exception(); 
    				}

					if (boardRequest.getSubject() != null && !boardRequest.getSubject().equals("")) {
						board.setSubject(boardRequest.getSubject());
					}
					if (boardRequest.getContent1() != null && !boardRequest.getContent1().equals("")) {
						board.setContent1(boardRequest.getContent1());
					}
					if (boardRequest.getSecret() != null && !boardRequest.getSecret().equals("")) {
						board.setSecret(boardRequest.getSecret());
					}
				}
	    	}

    	} catch (Exception e) {
            logger.error(bbsCode + " 게시판 게시물 수정 에러 - MemberKey 불일치", e.getMessage(), e);
		}

		return super.update(id, board);
	}

}
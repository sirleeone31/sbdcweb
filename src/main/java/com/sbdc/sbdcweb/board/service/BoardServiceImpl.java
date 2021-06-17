package com.sbdc.sbdcweb.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.SecretDomain;
import com.sbdc.sbdcweb.board.repository.BoardRepository;
import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 게시판 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Service
@Primary
public class BoardServiceImpl extends BaseServiceImpl<Board, Long> implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	protected BoardRepository boardRepository;
	protected BoardAttachService boardAttachService;
	protected CommonUtils commonUtils;
	protected FileManager fileManager;
	protected ImageManager imageManager;
	protected String boardCode;
	protected String bbsCode;
	protected String pathName;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, BoardAttachService boardAttachService, CommonUtils commonUtils, FileManager fileManager, ImageManager imageManager) {
    	super(boardRepository);
    	this.boardRepository = boardRepository;
    	this.boardAttachService = boardAttachService;
    	this.commonUtils = commonUtils;
    	this.fileManager = fileManager;
    	this.imageManager = imageManager;
    }

    /**
     * 대게시판 코드 설정 Getter
     */
    @Override
	public String getBoardCode() {
		return boardCode;
	}

    /**
     * 대게시판 코드 설정 Setter
     */
    @Override
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}

    /**
     * 게시판 코드 설정 Getter
     */
    @Override
	public String getBbsCode() {
		return bbsCode;
	}

    /**
     * 게시판 코드 설정 Setter
     */
    @Override
	public void setBbsCode(String bbsCode) {
		this.bbsCode = bbsCode;
	}

    /**
     * 게시판 경로 설정 Getter
     */
    @Override
	public String getPathName() {
		return pathName;
	}

    /**
     * 게시판 경로 설정 Setter
     */
    @Override
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	/**
     * 게시판 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
    @Override
    public List<Board> selectBoardList() {
    	List<Board> boardList = boardRepository.findByBbsCodeOrderByArticleKeyDesc(bbsCode);
    	return boardList;
    }

	/**
	 * 게시판 게시물 입력
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
		} catch (Exception e) {
            logger.error(bbsCode + " 게시판 게시물 입력 에러", e.getMessage(), e);
		}

        return board;
    }

	/**
	 * 게시판 게시물 및 첨부파일 입력
	 * 
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 파일 정보
	 * @param 	upload			Front에서 입력된 파일 객체
	 * @return	입력 로직이 적용된 board 자료
	 */
	@Override
    public Board insertBoard(Board boardRequest, BoardAttach attach, List<MultipartFile> upload) {
    	Board board = insertBoard(boardRequest);

    	// SBDC 뉴스 기본 이미지 적용
    	if (board.getBbsCode() == "dongjung" && upload.isEmpty()) {
    		MultipartFile mf = null;
       		boardAttachService.insertBoardAttach(new BoardAttach(), mf, board.getBbsCode(), board.getArticleKey());
    	}

    	if (!upload.isEmpty()) {
            for (MultipartFile mf : upload) {
                boardAttachService.insertBoardAttach(attach, mf, board.getBbsCode(), board.getArticleKey());
            }
        }

    	return board;
    }

	/**
	 * 게시판 게시물 및 첨부파일 수정
	 * 
	 * @param 	id				ARTICLE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 board 자료
	 * @param 	attach			Front에서 입력된 파일 정보
	 * @param 	upload			Front에서 입력된 파일 객체
	 * @return	수정 로직이 적용된 board 자료
	 */
    @Override
    public Board updateBoard(Long id, Board boardRequest, BoardAttach attach, List<MultipartFile> upload) {
    	Board board = null;

    	try {
        	if (boardRequest == null) {
        		throw new Exception();
        	}

        	board = boardRepository.save(boardRequest);

        	if (board != null) {
        		// 기존 데이터 삭제
        		boardAttachService.deleteBoardAttachByArticleKey(id);

        		// 새로운 파일 데이터 입력
        		if (!upload.isEmpty()) {
	                for (MultipartFile mf : upload) {
	                    boardAttachService.insertBoardAttach(attach, mf, board.getBbsCode(), board.getArticleKey());
	                }
	            }
        	}

    	} catch (Exception e) {
            logger.error(bbsCode + " 게시판 게시물 및 첨부파일 수정 에러", e.getMessage(), e);
		}

        return board;
    }

	/**
	 * 게시판 게시물 삭제
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	삭제 로직이 적용된 board 자료
	 */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Board board = super.select(id);

        if (board != null) {
            try {
            	boardMap.put("articleKey", board.getArticleKey());
        		boardRepository.delete(board);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error(bbsCode + " 게시판 게시물 삭제 에러", e.getMessage(), e);
        	}
        }

        boardMap.put("deleteYN", deleteYN);
        return boardMap;
    }

	/**
	 * 게시판 특정 회원 게시물 삭제
	 * 
	 * @param 	id			ARTICLE_KEY 값
	 * @param 	memberKey	MEMBER_KEY 값
	 * @return	삭제 로직이 적용된 board 자료
	 */
    @Override
    public Map<String, Object> delete(Long id, Long memberKey) {
        Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Board board = super.select(id);

        if (board != null) {
            try {
            	if (!memberKey.equals(board.getMemberKey())) {
					throw new Exception(); 
				}

            	boardMap.put("articleKey", board.getArticleKey());
            	boardRepository.delete(board);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error(bbsCode + " 게시판 특정 회원 게시물 삭제 에러", e.getMessage(), e);
        	}
        }

        boardMap.put("deleteYN", deleteYN);
        return boardMap;
    }

	/**
	 * 게시판 게시물 및 첨부파일 삭제
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	삭제 로직이 적용된 board 자료
	 */
    @Override
    public Map<String, Object> deleteBoard(Long id) {
        Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Board board = super.select(id);

        if (board != null) {
            try {
            	boardMap.put("articleKey", board.getArticleKey());
        		boardRepository.delete(board);
        		boardAttachService.deleteBoardAttachByArticleKey(board.getArticleKey());
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error(bbsCode + " 게시판 게시물 및 첨부파일 삭제 에러", e.getMessage(), e);
        	}
        }

        boardMap.put("deleteYN", deleteYN);
        return boardMap;
    }

    /**
     * 게시판 첨부파일 다운로드
     *
     * @param 	attachKey	ATTACH_KEY 값
     * @return 	파일 정보 전송
     */
    @Override
    public Map<String, Object> downloadBoard(Long attachKey) {
        Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean downloadYN = false;

        BoardAttach boardAttach = boardAttachService.selectBoardAttach(attachKey);

        if (boardAttach != null) {
	        try {
	            // 파일서버 이전하기 전 임시처리
	            fileManager.doFileServerInsert(boardAttach.getGuidName(), boardAttach.getFileName(), getPathName(), boardCode, bbsCode);

	            boardMap.put("originalFilename", boardAttach.getFileName());
	        	boardMap.put("saveFilename", boardAttach.getGuidName());
	        	boardMap.put("pathName", pathName);
	        	downloadYN = true;
		    } catch (Exception e) {
	            logger.error(bbsCode + " 게시판 첨부파일 다운로드 에러", e.getMessage(), e);
	    	}
    	}

        boardMap.put("downloadYN", downloadYN);
        return boardMap;
	}

    /**
     * 게시판 이미지 불러오기
     *
     * @param 	attachKey	ATTACH_KEY 값
     * @param 	thumb 		썸네일 사용 여부
     * @return	이미지 파일 전송
     */
	@Override
	public Map<String, Object> selectImage(Long attachKey, String thumb) {
        File file = null;
        InputStreamResource inputStreamResource = null;
        Map<String, Object> imageMap = new HashMap<String, Object>();

        try {
            BoardAttach boardAttach = boardAttachService.selectBoardAttach(attachKey);
            String originalFilename = boardAttach.getFileName();
            String guidName = boardAttach.getGuidName();
            String fileEXT = boardAttach.getFileExt();

            // 파일서버 이전하기 전 임시처리
            fileManager.doFileServerInsert(guidName, originalFilename, getPathName(), boardCode, bbsCode);

            if (thumb == null) {
                file = new File(getPathName() + File.separator + guidName);
                // 이미지 resize 임시 처리 -> 차후 이미지 생성 시 적용
                file = imageManager.ImageResize(file, getPathName(), guidName, fileEXT);
            } else if (thumb.equalsIgnoreCase("Y")) {
                file = new File(getPathName() + File.separator + "thumb" + File.separator + guidName);
            }

        	inputStreamResource = new InputStreamResource(new FileInputStream(file));
            imageMap.put("file", file);
            imageMap.put("fileEXT", fileEXT);
            imageMap.put("inputStreamResource", inputStreamResource);
        } catch (FileNotFoundException e) {
            logger.error(bbsCode + " 게시판 이미지 불러오기 에러1 - FileNotFound", e.getMessage(), e);
        } catch (Exception e) {
            logger.error(bbsCode + " 게시판 이미지 불러오기 에러2", e.getMessage(), e);
        }

        return imageMap;
    }

	/**
	 * 게시판 최상위 ARTICLE_NO 조회
	 * 
	 * @return	해당 BBS_CODE의 최상위 ARTICLE_NO 값
	 */
    @Override
	public Long maxArticleNo() {
        Long maxArticleNo = boardRepository.selectMaxArticleNoByBbsCode(bbsCode);
        maxArticleNo = maxArticleNo+1L;
        return maxArticleNo;
	}

    /**
	 * 게시판 회원 비밀글 조회
	 * 
	 * @param 	id	ARTICLE_KEY 값
	 * @return	해당 게시물의 비밀글 여부와 MEMBER_KEY 값
	 */
    @Override
	public SecretDomain secretArticle(Long id) {
    	SecretDomain secretDomain = null;

    	try {
    		Board board = super.select(id);
    		if (board != null) {
        		secretDomain = new SecretDomain(board.getMemberKey(), board.getSecret());
        	} 
    	} catch (Exception e) {
    		logger.error(bbsCode + " 게시판 회원 비밀글 조회 에러", e.getMessage(), e);
		}

		return secretDomain;
	}

    /**
	 * 서버에 접속한 사용자 IP 설정
	 * 
	 * @param 	ip				ip 값
	 * @param 	boardRequest	ip 값을 설정할 board 자료
	 */
    @Override
	public void insertIp(String ip, Board boardRequest) {
    	boardRequest.setIp(ip);
	}

}
package com.sbdc.sbdcweb.board.attach.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.attach.repository.BoardAttachRepository;
import com.sbdc.sbdcweb.board.domain.BoardAttach;
import com.sbdc.sbdcweb.board.domain.response.AttachAllDto;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.common.ImageManager;

/**
 * 첨부파일 ServiceImpl
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-29
 */
@Service
public class BoardAttachServiceImpl implements BoardAttachService {
	private static final Logger logger = LoggerFactory.getLogger(BoardAttachServiceImpl.class);

	private final BoardAttachRepository boardAttachRepository;
	private final FileManager fileManager;
	private final ImageManager imageManager;

    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + "bbs" + File.separator;

//	private String PATH_NAME = "D:\\WebServer\\SBDC_WEB\\upload\\bbs\\";
	
	@Autowired
	public BoardAttachServiceImpl(BoardAttachRepository boardAttachRepository, FileManager fileManager, ImageManager imageManager) {
		this.boardAttachRepository = boardAttachRepository;
		this.fileManager = fileManager;
		this.imageManager = imageManager;
	}

	/**
     * 첨부파일 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 boardAttach 자료
     */
	@Override
	public List<BoardAttach> selectBoardAttachList() {
		List<BoardAttach> boardAttachList = boardAttachRepository.findAllByOrderByAttachKeyDesc();
		return boardAttachList;
	}

	/**
     * 첨부파일 전체목록 조회 - articleKey 조건
     * 
	 * @param	articleKey	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 boardAttach 자료
     */
	@Override
	public List<BoardAttach> selectBoardAttachList(Long articleKey) {
		List<BoardAttach> boardAttachList = boardAttachRepository.findByArticleKeyOrderBySeq(articleKey);
		return boardAttachList;
	}

    /**
	 * 첨부파일 특정 조회
	 * 
	 * @param 	id	ATTACH_KEY 값
	 * @return	조회 로직이 적용된 boardAttach 자료
	 */
	@Override
	public BoardAttach selectBoardAttach(Long id) {
		BoardAttach boardAttach = null;

		try {
			boardAttach = boardAttachRepository.findById(id).get();
		} catch (NoSuchElementException e) {
            logger.error("첨부파일 특정 조회 에러", e.getMessage(), e);
		}

		return boardAttach;
	}

	/**
	 * 첨부파일 특정 조회 - articleKey 조건
	 * 
	 * @param 	articleKey	ARTICLE_KEY 값
	 * @return	조회 로직이 적용된 boardAttach 자료
	 */
	@Override
	public BoardAttach selectBoardAttachByArticleKey(Long articleKey) {
		BoardAttach boardAttach = null;

		try {
			boardAttach = boardAttachRepository.findTopByArticleKey(articleKey);
		} catch (NoSuchElementException e) {
            logger.error("첨부파일 특정 조회 articleKey 첫번째 SEQ 에러", e.getMessage(), e);
		}

		return boardAttach;
	}

	/**
	 * 첨부파일 입력
	 * 
	 * @param 	boardAttachRequest	Front에서 넘어온 boardAttach 자료
	 * @param 	upload				Front에서 넘어온 파일
	 * @param 	bbsCode				입력할 board 구분
	 * @param 	articleKey			ARTICLE_KEY 값
	 * @return	입력 로직이 적용된 boardAttach 자료
	 */
	@Override
	public BoardAttach insertBoardAttach(BoardAttach boardAttachRequest, MultipartFile upload, String bbsCode, Long articleKey) {
		BoardAttach boardAttach = null;

		// SBDC 뉴스 기본 이미지 적용
		if (bbsCode == "dongjung" && upload == null) {
			try {
				boardAttachRequest.setAttachKey(maxAttachKey());

				// 첨부파일명
				boardAttachRequest.setFileName("logo.png");

				// 첨부파일 크기(byte)
				boardAttachRequest.setFileSize(10000L);

				// 첨부파일 확장자
				boardAttachRequest.setFileExt(boardAttachRequest.getFileName().substring(boardAttachRequest.getFileName().lastIndexOf(".")+1));

				// 첨부파일 고유 GUID 입력 및 파일 업로드
				boardAttachRequest.setGuidName("default");

				// 첨부파일 이미지 여부 확인
				if(boardAttachRequest.getFileExt().equalsIgnoreCase("jpg") || boardAttachRequest.getFileExt().equalsIgnoreCase("jpeg") || boardAttachRequest.getFileExt().equalsIgnoreCase("bmp") || boardAttachRequest.getFileExt().equalsIgnoreCase("png") || boardAttachRequest.getFileExt().equalsIgnoreCase("gif")) {
					boardAttachRequest.setIsImage("Y");
				} else {
					boardAttachRequest.setIsImage("N");
				}

				// 등록된 게시판 코드 입력
				boardAttachRequest.setBbsCode(bbsCode);

				// Article_key 입력
				boardAttachRequest.setArticleKey(articleKey);

				// Seq입력
				boardAttachRequest.setSeq(1L);

				// 데이터 저장
				boardAttach = boardAttachRepository.save(boardAttachRequest);	
			} catch (Exception e) {
	            logger.error("게시판 첨부파일 데이터 입력 에러", e.getMessage(), e);
			}

		} else {
			try {
				boardAttachRequest.setAttachKey(maxAttachKey());
	
	//			SQL Server 버전 업 후, 위 두 구문과 주석 교체
	//			BoardAttach topAttach = boardAttachRepository.findTop1ByOrderByAttachKeyDesc();
	//			attach.setAttachKey(topAttach.getAttachKey()+1);
	
				// 첨부파일명
				if(upload.getOriginalFilename().contains(File.separator)) {
					// IE의 경우 불필요한 파일 경로가 포함되므로 파일 이름만 추출한다.
					boardAttachRequest.setFileName(upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(File.separator)+1));
				} else {
					boardAttachRequest.setFileName(upload.getOriginalFilename());
				}
	
				// 첨부파일 크기(byte)
				boardAttachRequest.setFileSize(upload.getSize());
	
				// 첨부파일 확장자
				boardAttachRequest.setFileExt(boardAttachRequest.getFileName().substring(boardAttachRequest.getFileName().lastIndexOf(".")+1));
	
				// 첨부파일 고유 GUID 입력 및 파일 업로드
				boardAttachRequest.setGuidName(fileManager.doFileUpload(upload, pathName + bbsCode));
	
				if(bbsCode.equalsIgnoreCase("dongjung") || bbsCode.equalsIgnoreCase("hongbo")) {
					// 썸네일 이미지 처리를 위한 파일 Resize
					File file = new File(pathName + bbsCode + File.separator + boardAttachRequest.getGuidName());
					file = imageManager.ImageResize(file, pathName + bbsCode + File.separator, boardAttachRequest.getGuidName(), boardAttachRequest.getFileExt());
				}
	
				// 첨부파일 이미지 여부 확인
				if(boardAttachRequest.getFileExt().equalsIgnoreCase("jpg") || boardAttachRequest.getFileExt().equalsIgnoreCase("jpeg") || boardAttachRequest.getFileExt().equalsIgnoreCase("bmp") || boardAttachRequest.getFileExt().equalsIgnoreCase("png") || boardAttachRequest.getFileExt().equalsIgnoreCase("gif")) {
					boardAttachRequest.setIsImage("Y");
				} else {
					boardAttachRequest.setIsImage("N");
				}
	
				// 등록된 게시판 코드 입력
				boardAttachRequest.setBbsCode(bbsCode);
	
				// Article_key 입력
				boardAttachRequest.setArticleKey(articleKey);
	
				// Seq입력(logic 수정)
				Long count = boardAttachRepository.countByArticleKey(articleKey);
	
				if (count == 0L) {
					boardAttachRequest.setSeq(1L);
				} else {
					Long seq = boardAttachRepository.selectMaxSeqByArticleKey(articleKey);
					boardAttachRequest.setSeq(seq+1L);
				}
	
				// 데이터 저장
				boardAttach = boardAttachRepository.save(boardAttachRequest);	
			} catch (Exception e) {
	            logger.error("게시판 첨부파일 데이터 입력 에러", e.getMessage(), e);
			}
		}

		return boardAttach;
	}

	/**
	 * 첨부파일 삭제
	 * 
	 * @param 	id	ATTACH_KEY 값
	 * @return	삭제 로직이 적용된 boardAttach 자료
	 */
	@Override
	public Map<String, Object> deleteBoardAttach(Long id) {
        Map<String, Object> boardAttachMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        BoardAttach boardAttach = boardAttachRepository.findById(id).get();

        if (boardAttach != null) {
            try {
            	boardAttachMap.put("attachKey", boardAttach.getAttachKey());
            	boardAttachRepository.delete(boardAttach);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error("첨부파일 삭제 에러", e.getMessage(), e);
        	}
        }

        boardAttachMap.put("deleteYN", deleteYN);
        return boardAttachMap;
	}

	/**
	 * 첨부파일 삭제 - articleKey 조건
	 * 
	 * @param 	articleKey	ARTICLE_KEY 값
	 * @return	삭제 로직이 적용된 boardAttach 자료
	 */
	@Override
	public Map<String, Object> deleteBoardAttachByArticleKey(Long articleKey) {
        Map<String, Object> boardAttachMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        List<BoardAttach> boardAttachList = boardAttachRepository.findByArticleKeyOrderBySeq(articleKey);

        if (boardAttachList.isEmpty() == false) {
            try {
    			for(BoardAttach boardAttach : boardAttachList) {
    				boardAttachRepository.delete(boardAttach);
    			}
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error("특정 첨부파일 삭제 에러", e.getMessage(), e);
        	}
        }

        boardAttachMap.put("deleteYN", deleteYN);
        return boardAttachMap;
	}

	/**
     * 첨부파일 메인 일부목록 조회
     * 
	 * @return	조회 로직이 적용된 boardAttach 자료
     */
	@Override
	public List<AttachAllDto> selectBoardAttachListMain() {
        List<AttachAllDto> boardAttachMainList = new ArrayList<AttachAllDto>();
		List<BoardAttach> boardAttachList = boardAttachRepository.findByBbsCodeAndSeqOrderByAttachKeyDesc("dongjung",1L);
//      SQL Server 버전 업 후 PageRequest 로 변경

		for (BoardAttach boardAttach : boardAttachList) {
			boardAttachMainList.add(new AttachAllDto(
		    		 boardAttach.getAttachKey(),
		    		 boardAttach.getGuidName(),
		    		 boardAttach.getArticleKey(),
		    		 boardAttach.getFileExt()));
		}


//		boardAttachMainList.add(0,boardAttachList.get(0));
//        attachTopMain.add(1,attachMain.get(1));

		return boardAttachMainList;
//		return boardAttachRepository.getTop1DongjungAttach(PageRequest.of(0, 2));
	}

	/**
	 * 첨부파일 최상위 ATTACH_KEY 조회
	 * 
	 * @return	최상위 ATTACH_KEY 값
	 */
    @Override
	public Long maxAttachKey() {
        Long maxAttachKey = boardAttachRepository.selectMaxAttachKey();
        maxAttachKey = maxAttachKey+1L;
        return maxAttachKey;
	}

}
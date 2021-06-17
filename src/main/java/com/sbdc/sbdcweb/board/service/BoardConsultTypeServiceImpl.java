package com.sbdc.sbdcweb.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.board.domain.BoardConsultType;
import com.sbdc.sbdcweb.board.repository.BoardConsultTypeRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 입점 및 판매상담 카테고리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-19
 */
@Service
public class BoardConsultTypeServiceImpl extends BaseServiceImpl<BoardConsultType, Long> implements BoardConsultTypeService {
	private static final Logger logger = LoggerFactory.getLogger(BoardConsultTypeServiceImpl.class);

	private final BoardConsultTypeRepository boardConsultTypeRepository;
    private final String bbsCode = "consulttype";

    @Autowired
    public BoardConsultTypeServiceImpl(BoardConsultTypeRepository boardConsultTypeRepository) {
    	super(boardConsultTypeRepository);
        this.boardConsultTypeRepository = boardConsultTypeRepository;
    }

    /**
     * 입점 및 판매상담 카테고리 코드 설정 Getter
     */
    @Override
	public String getBbsCode() {
		return bbsCode;
	}

    /**
     * 입점 및 판매상담 카테고리 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 ConsultType 자료
     */
    @Override
    public List<BoardConsultType> selectList() {
    	List<BoardConsultType> boardList = boardConsultTypeRepository.findAllByOrderBySeqAsc();
    	return boardList;
    }

	/**
	 * 입점 및 판매상담 카테고리 입력
	 * 
	 * @param 	boardRequest	Front에서 입력된 자료
	 * @return	입력 로직이 적용된 ConsultType 자료
	 */
    @Override
	public BoardConsultType insert(BoardConsultType boardRequest) {
    	BoardConsultType boardConsultType = null;

    	try {
    		boardRequest.setSeq(maxArticleNo());
    		boardConsultType = boardConsultTypeRepository.save(boardRequest);
		} catch (Exception e) {
            logger.error("입점 및 판매상담 카테고리 입력 에러", e.getMessage(), e);
		}

		return boardConsultType;
	}

    /**
	 * 입점 및 판매상담 카테고리 수정
	 * 
	 * @param 	id				TYPE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 ContractType 자료
	 * @return	수정 로직이 적용된 ConsultType 자료
	 */
    @Override
	public BoardConsultType update(Long id, BoardConsultType boardRequest) {
    	BoardConsultType boardConsultType = super.select(id);

    	if (boardConsultType != null) {
			if (boardRequest.getName() != null && !boardRequest.getName().equals("")) {
				boardConsultType.setName(boardRequest.getName());
			}
			if (boardRequest.getEmail() != null && !boardRequest.getEmail().equals("")) {
				boardConsultType.setEmail(boardRequest.getEmail());
			}
			if (boardRequest.getSeq() != null && !boardRequest.getSeq().equals(0L)) {
				boardConsultType.setSeq(boardRequest.getSeq());
			}
    	}

		return super.update(id, boardConsultType);
    }

    /**
	 * 입점 및 판매상담 카테고리 삭제
	 * 
	 * @param 	id		TYPE_KEY 값
	 * @return	삭제 로직이 적용된 ConsultType 자료
	 */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        BoardConsultType boardConsultType = super.select(id);

    	if (boardConsultType != null) {
	        try {
	        	boardMap.put("typeKey", boardConsultType.getTypeKey());
	        	boardConsultTypeRepository.delete(boardConsultType);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("입점 및 판매상담 카테고리 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	boardMap.put("deleteYN", deleteYN);
        return boardMap;
    }

    /**
	 * 최상위 SEQ 조회
	 * 
	 * @return	최상위 SEQ 증가값
	 */
    @Override
	public Long maxArticleNo() {
        Long maxArticleNo = boardConsultTypeRepository.selectMaxSeq();
        maxArticleNo = maxArticleNo+1L;
        return maxArticleNo;
	}

}
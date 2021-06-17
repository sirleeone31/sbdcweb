package com.sbdc.sbdcweb.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.board.domain.BoardContractType;
import com.sbdc.sbdcweb.board.repository.BoardContractTypeRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 입찰정보 카테고리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-17
 */
@Service
public class BoardContractTypeServiceImpl extends BaseServiceImpl<BoardContractType, Long> implements BoardContractTypeService {
	private static final Logger logger = LoggerFactory.getLogger(BoardContractTypeServiceImpl.class);

	private final BoardContractTypeRepository boardContractRepository;
    private final String bbsCode = "contracttype";

    @Autowired
    public BoardContractTypeServiceImpl(BoardContractTypeRepository boardContractRepository) {
    	super(boardContractRepository);
    	this.boardContractRepository = boardContractRepository;
    }

    /**
     * 입찰정보 카테고리 코드 설정 Getter
     */
    @Override
	public String getBbsCode() {
		return bbsCode;
	}

    /**
     * 입찰정보 카테고리 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 ContractType 자료
     */
    @Override
    public List<BoardContractType> selectList() {
    	List<BoardContractType> boardList = boardContractRepository.findAllByOrderBySeqAsc();
    	return boardList;
    }

	/**
	 * 입찰정보 카테고리 입력
	 * 
	 * @param 	boardRequest	Front에서 입력된 자료
	 * @return	입력 로직이 적용된 ContractType 자료
	 */
    @Override
	public BoardContractType insert(BoardContractType boardRequest) {
    	BoardContractType boardContractType = null;

    	try {
    		boardRequest.setSeq(maxArticleNo());
    		boardContractType = boardContractRepository.save(boardRequest);
		} catch (Exception e) {
            logger.error("입찰정보 카테고리 입력 에러", e.getMessage(), e);
		}

		return boardContractType;
	}

    /**
	 * 입찰정보 카테고리 수정
	 * 
	 * @param 	id				TYPE_KEY 값
	 * @param 	boardRequest	Front에서 입력된 ContractType 자료
	 * @return	수정 로직이 적용된 ContractType 자료
	 */
    @Override
	public BoardContractType update(Long id, BoardContractType boardRequest) {
    	BoardContractType boardContractType = super.select(id);

    	if (boardContractType != null) {
			if (boardRequest.getName() != null && !boardRequest.getName().equals("")) {
				boardContractType.setName(boardRequest.getName());
			}
			if (boardRequest.getSeq() != null && !boardRequest.getSeq().equals(0L)) {
				boardContractType.setSeq(boardRequest.getSeq());
			}
    	}

		return super.update(id, boardContractType);
    }

    /**
	 * 입찰정보 카테고리 삭제
	 * 
	 * @param 	id		TYPE_KEY 값
	 * @return	삭제 로직이 적용된 ContractType 자료
	 */
    @Override
    public Map<String, Object> delete(Long id) {
    	Map<String, Object> boardMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        BoardContractType boardContractType = super.select(id);

    	if (boardContractType != null) {
	        try {
	        	boardMap.put("typeKey", boardContractType.getTypeKey());
	        	boardContractRepository.delete(boardContractType);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("입찰정보 카테고리 삭제 에러", e.getMessage(), e);
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
        Long maxArticleNo = boardContractRepository.selectMaxSeq();
        maxArticleNo = maxArticleNo+1L;
        return maxArticleNo;
	}

}
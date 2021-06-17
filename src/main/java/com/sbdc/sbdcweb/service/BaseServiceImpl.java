package com.sbdc.sbdcweb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * 공통 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@Service
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	protected BaseRepository<T, ID> baseRepository;

    @Autowired
    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

	/**
     * 공통 전체 조회
     * 
	 * @return	조회 로직이 적용된 자료
     */
    @Override
    public List<T> selectList() {
    	List<T> selectList = baseRepository.findAll();
    	return selectList;
    }

    /**
	 * 공통 특정 조회
	 * 
	 * @param 	id	PK 값
	 * @return	조회 로직이 적용된 자료
	 */
    @Override
    public T select(ID id) {
    	T select = null;

    	try {
    		select = baseRepository.findById(id).get();
    	} catch (NoSuchElementException e) {
            logger.error("특정 조회 에러", e.getMessage(), e);
		}

    	return select;
    }

	/**
	 * 공통 입력
	 * 
	 * @param 	dtoRequest	Front에서 입력된 자료
	 * @return	입력 로직이 적용된 자료
	 */
    @Override
    public T insert(T dtoRequest) {
    	T insert = null;

    	try {
    		insert = baseRepository.save(dtoRequest);
		} catch (Exception e) {
            logger.error("입력 에러", e.getMessage(), e);
		}

        return insert;
    }

	/**
	 * 공통 수정
	 * 
	 * @param 	id			PK 값
	 * @param 	dtoRequest	Front에서 입력된 자료
	 * @return	수정 로직이 적용된 자료
	 */
    @Override
    public T update(ID id, T dtoRequest) {
    	T update = null;

    	try {
        	if (dtoRequest == null) {
        		throw new Exception();
        	}

        	update = baseRepository.save(dtoRequest);
		} catch (Exception e) {
            logger.error("수정 에러", e.getMessage(), e);
		}

        return update;
    }

	/**
	 * 공통 삭제
	 * 
	 * @param 	id	PK 값
	 * @return	삭제 로직이 적용된 자료
	 */
    @Override
    public Map<String, Object> delete(ID id) {
        Map<String, Object> deleteMap = new HashMap<String, Object>();
        boolean deleteYN = false;

        try {
    		T delete = select(id);
    		baseRepository.delete(delete);
	    	deleteYN = true;
    	} catch (Exception e) {
    		logger.error("삭제 에러", e.getMessage(), e);
    	}

    	deleteMap.put("deleteYN", deleteYN);

    	return deleteMap;
    }

}
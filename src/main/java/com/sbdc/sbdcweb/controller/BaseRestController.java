package com.sbdc.sbdcweb.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sbdc.sbdcweb.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공통 Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public abstract class BaseRestController<T, ID> {
	protected BaseService<T, ID> baseService;

	@Autowired
    public BaseRestController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    /**
     * 공통 전체 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    public ResponseEntity<List<T>> selectList(HttpServletRequest request) {
		List<T> selectList = baseService.selectList();

		if (selectList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(selectList);
	}

    /**
	 * 공통 특정 조회
	 * 
	 * @param 	request	HttpServletRequest 객체
	 * @param 	id		PK 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> select(HttpServletRequest request, ID id) {
    	T select = baseService.select(id);

		if (select == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(select);
	}

	/**
	 * 공통 입력
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	dtoRequest	Front에서 입력된 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> insert(HttpServletRequest request, T dtoRequest) {
    	T insert = baseService.insert(dtoRequest);

		if (insert == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 공통 수정
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			PK 값
	 * @param 	dtoRequest	Front에서 입력된 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> update(HttpServletRequest request, ID id, T dtoRequest) {
		T update = baseService.update(id, dtoRequest);

		if (update == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 공통 삭제
	 * 
	 * @param 	request	HttpServletRequest 객체
	 * @param 	id		PK 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    public ResponseEntity<T> delete(HttpServletRequest request, ID id) {
    	Map<String, Object> deleteMap = baseService.delete(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
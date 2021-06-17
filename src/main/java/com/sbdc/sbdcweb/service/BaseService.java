package com.sbdc.sbdcweb.service;

import java.util.List;
import java.util.Map;

/**
 * 공통 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-24
 */
public interface BaseService<T, ID> {
	public List<T> selectList();
	public T select(ID id);
	public T insert(T dtoRequest);
	public T update(ID id, T dtoRequest);
	public Map<String, Object> delete(ID id);
}
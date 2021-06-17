package com.sbdc.sbdcweb.info.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.service.BaseService;

/**
 * 정보공개 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
public interface InfoService<T, ID> extends BaseService<T, ID> {
	public String getInfoCode();
	public void setInfoCode(String infoCode);
	public String getPathName();
	public void setPathName(String pathName);
	public String getBbsCode();
	public void setBbsCode(String bbsCode);

	public List<T> selectInfoList();
	public T insertInfo(T infoRequest, MultipartFile upload);
	public T updateInfo(ID id, T infoRequest, MultipartFile upload);

}
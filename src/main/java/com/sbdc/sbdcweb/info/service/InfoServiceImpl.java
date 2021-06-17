package com.sbdc.sbdcweb.info.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.board.attach.service.BoardAttachService;
import com.sbdc.sbdcweb.info.repository.InfoRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 정보공개 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@Service
public abstract class InfoServiceImpl<T, ID> extends BaseServiceImpl<T, ID> implements InfoService<T, ID> {
	private static final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

	protected InfoRepository<T, ID> infoRepository;
	protected BoardAttachService boardAttachService;
	protected String infoCode;
	protected String pathName;
	protected String bbsCode;

//    @Autowired
//    public InfoServiceImpl(BaseRepository<T, ID> baseRepository) {
//    	super(baseRepository);
//    	this.infoRepository = (InfoRepository) baseRepository;
//    }

    @Autowired
    public InfoServiceImpl(InfoRepository<T, ID> infoRepository) {
    	super(infoRepository);
    	this.infoRepository = infoRepository;
    }

    /**
     * 정보공개 코드 설정 Getter
     */
    @Override
	public String getInfoCode() {
		return infoCode;
	}

    /**
     * 정보공개 코드 설정 Setter
     */
    @Override
	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}

    /**
     * 정보공개 경로 설정 Getter
     */
    @Override
	public String getPathName() {
		return pathName;
	}

    /**
     * 정보공개 경로 설정 Setter
     */
    @Override
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

    /**
     * 하위 게시판 코드 설정 Getter
     */
    @Override
	public String getBbsCode() {
		return bbsCode;
	}

    /**
     * 하위 게시판 코드 설정 Setter
     */
    @Override
	public void setBbsCode(String bbsCode) {
		this.bbsCode = bbsCode;
	}

    /**
     * 정보공개 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 info 자료
     */
	@Override
	public List<T> selectInfoList() {
        List<T> selectList = infoRepository.findAllByOrderByInfoKeyDesc();
        return selectList;
	}

	/**
	 * 정보공개 게시물 입력
	 * 
	 * @param 	infoRequest	Front에서 입력된 info 자료
	 * @param 	upload		Front에서 입력된 파일 객체
	 * @return	입력 로직이 적용된 info 자료
	 */
	@Override
	public T insertInfo(T infoRequest, MultipartFile upload) {
		T insert = null;

    	try {
        	insert = infoRepository.save(infoRequest);
		} catch (Exception e) {
            logger.error(infoCode + " 게시물 입력 에러", e.getMessage(), e);
		}

        return insert;
	}

	/**
	 * 정보공개 게시물 수정
	 * 
	 * @param 	id			INFO_KEY 값
	 * @param 	infoRequest	Front에서 입력된 info 자료
	 * @param 	upload		Front에서 입력된 파일 객체
	 * @return	수정 로직이 적용된 info 자료
	 */
	@Override
	public T updateInfo(ID id, T infoRequest, MultipartFile upload) {
    	T update = null;

    	try {
        	if (infoRequest == null) {
        		throw new Exception();
        	}

        	update = infoRepository.save(infoRequest);
		} catch (Exception e) {
            logger.error(infoCode + " 게시물 수정 에러", e.getMessage(), e);
		}

        return update;
	}

}
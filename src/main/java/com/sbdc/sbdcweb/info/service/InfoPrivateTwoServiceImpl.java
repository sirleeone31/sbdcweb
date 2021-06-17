package com.sbdc.sbdcweb.info.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.domain.InfoPrivateTwo;
import com.sbdc.sbdcweb.info.repository.InfoPrivateTwoRepository;

/**
 * 계약현황관리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Service
public class InfoPrivateTwoServiceImpl extends InfoServiceImpl<InfoPrivateTwo, Long> implements InfoPrivateTwoService {
	private static final Logger logger = LoggerFactory.getLogger(InfoPrivateTwoServiceImpl.class);

	private final InfoPrivateTwoRepository infoPrivateTwoRepository;
    private final FileManager fileManager;
    private final String infoCode = "infoprivate2";
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + infoCode;

    @Autowired
    public InfoPrivateTwoServiceImpl(InfoPrivateTwoRepository infoPrivateTwoRepository, FileManager fileManager) {
        super(infoPrivateTwoRepository);
        super.setInfoCode(infoCode);
        super.setPathName(pathName);
        super.setBbsCode("");
        this.infoPrivateTwoRepository = infoPrivateTwoRepository;
        this.fileManager = fileManager;
    }

    /**
     * 계약현황관리 전체목록 조회
     * 
     * @return  조회 로직이 적용된 infoList 자료
     */
    @Override
    public List<InfoPrivateTwo> selectInfoList() {
        List<InfoPrivateTwo> infoList = super.selectInfoList();

        int j = 0;

        for (int i = infoList.size(); i > 0; i--) {
            infoList.get(j).setNum(i);
            j++;
        }

        return infoList;
    }

	/**
	 * 계약현황관리 게시물 입력
	 * 
	 * @param 	infoRequest	Front에서 입력된 info 자료
	 * @param 	upload		Front에서 입력받은 파일 객체
	 * @return	입력 로직이 적용된 info 자료
	 */
    @Override
    public InfoPrivateTwo insertInfo(InfoPrivateTwo infoRequest, MultipartFile upload) {
    	if (upload != null) {
            // 업로드 파일명
        	infoRequest.setFileName(upload.getOriginalFilename());

            // 업로드 파일크기
        	infoRequest.setFileSize(upload.getSize());

            // 업로드 파일 확장자
        	infoRequest.setFileExt(infoRequest.getFileName().substring(infoRequest.getFileName().lastIndexOf(".") + 1));

        	// 업로드 파일 고유 GUID 입력 및 파일 업로드
        	try {
				infoRequest.setGuidName(fileManager.doFileUpload(upload, pathName));
			} catch (Exception e) {
				logger.error(infoCode + " 게시물 입력 에러 - FileUpload", e.getMessage(), e);
			}
		}

		return super.insertInfo(infoRequest, upload);
    }

    /**
	 * 계약현황관리 게시물 수정
     * 
     * @param   id             	INFO_KEY 값
     * @param   infoRequest    	Front에서 입력된 info 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
     * @return  갱신 로직이 적용된 info 자료
     */
    @Override
    public InfoPrivateTwo updateInfo(Long id, InfoPrivateTwo infoRequest, MultipartFile upload) {
    	InfoPrivateTwo info = super.select(id);
    	
    	if (info != null) {
        	if (infoRequest.getSubject() != null && !infoRequest.getSubject().equals("")) {
    			info.setSubject(infoRequest.getSubject());
    		}
    		if (infoRequest.getDeptName() != null && !infoRequest.getDeptName().equals("")) {
    			info.setDeptName(infoRequest.getDeptName());
    		}

    		// 파일 포함 전체정보 변경
    		if (upload != null) {
    			// 변경된 파일정보
    			info.setFileName(upload.getOriginalFilename());
    			info.setFileSize(upload.getSize());
    			info.setFileExt(upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1));

            	// GUID 변경 및 파일 업로드
    			try {
    				info.setGuidName(fileManager.doFileUpload(upload, pathName));
    			} catch (Exception e) {
    				logger.error(infoCode + " 게시물 수정 에러 - FileUpload", e.getMessage(), e);
    			}
            }
    	}

		return super.updateInfo(id, info, upload);
    }

    /**
	 * 계약현황관리 게시물 삭제
     * 
     * @param   id          INFO_KEY 값
     * @return  삭제 로직이 적용된 info 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        InfoPrivateTwo info = super.select(id);

    	if (info != null) {
	        try {
	        	infoMap.put("infoKey", info.getInfoKey());
	        	infoPrivateTwoRepository.delete(info);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error(infoCode + " 게시물 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	infoMap.put("deleteYN", deleteYN);
        return infoMap;
    }

    /**
	 * 계약현황관리 첨부파일 다운로드
	 * 
	 * @param 	id		INFO_KEY 값
     * @return  다운로드에 필요한 변수 값
	 */
	@Override
	public Map<String, Object> downloadInfo(Long id) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        boolean downloadYN = false;
        InfoPrivateTwo info = super.select(id);

        if (info != null) {
	        try {
	            // 파일서버 이전하기 전 임시처리
	            fileManager.doFileServerInsert(info.getGuidName(), info.getFileName(), getPathName(), infoCode, getBbsCode());

	        	infoMap.put("originalFilename", info.getFileName());
	        	infoMap.put("saveFilename", info.getGuidName());
	        	infoMap.put("pathName", pathName);
	        	downloadYN = true;
		    } catch (Exception e) {
	            logger.error(infoCode + " 첨부파일 다운로드 에러", e.getMessage(), e);
	    	}
    	}

        infoMap.put("downloadYN", downloadYN);
        return infoMap;
	}

}
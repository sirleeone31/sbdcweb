package com.sbdc.sbdcweb.info.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.info.repository.InfoBusinessRepository;

/**
 * 기관홍보영상 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2020-08-15
 */
@Service
public class InfoIntroductionServiceImpl implements InfoIntroductionService {
	private static final Logger logger = LoggerFactory.getLogger(InfoIntroductionServiceImpl.class);

    private final FileManager fileManager;
    private final String infoCode = "info";
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + infoCode;

	@Autowired
    public InfoIntroductionServiceImpl(InfoBusinessRepository infoBusinessRepository, FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
	 * 기관홍보영상 첨부파일 다운로드
	 * 
     * @return  다운로드에 필요한 변수 값
	 */
	@Override
	public Map<String, Object> downloadInfo() {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        boolean downloadYN = false;

        try {
            // 파일서버 이전하기 전 임시처리
            fileManager.doFileServerInsert("hongbo", "hongbo.mp4", pathName, infoCode, "");

            infoMap.put("originalFilename", "hongbo.mp4");
        	infoMap.put("saveFilename", "hongbo");
        	infoMap.put("pathName", pathName);
        	downloadYN = true;
	    } catch (Exception e) {
            logger.error(infoCode + " 첨부파일 다운로드 에러", e.getMessage(), e);
    	}

        infoMap.put("downloadYN", downloadYN);
        return infoMap;
	}

}
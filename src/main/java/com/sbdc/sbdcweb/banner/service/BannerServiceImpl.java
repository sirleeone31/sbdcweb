package com.sbdc.sbdcweb.banner.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.banner.domain.Banner;
import com.sbdc.sbdcweb.banner.repository.BannerRepository;
import com.sbdc.sbdcweb.common.FileManager;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 배너 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date 	: 2018-12-21
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner, Long> implements BannerService {
	private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);

	private final BannerRepository bannerRepository;
    private final FileManager fileManager;
    private final String pathName = "D:" + File.separator + "WebServer"  + File.separator + "SBDC_WEB"  + File.separator +  "upload" + File.separator + "banner";

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository, FileManager fileManager) {
        super(bannerRepository);
    	this.bannerRepository = bannerRepository;
        this.fileManager = fileManager;
    }

    /**
     * 배너 전체목록 조회
     * 
     * @return  조회 로직이 적용된 bannerList 자료
     */
    @Override
    public List<Banner> selectList() {
    	List<Banner> bannerList = bannerRepository.findAllByOrderByTypeAscSeqAsc();
        return bannerList;
    }

	/**
	 * 배너 입력
	 * 
	 * @param 	bannerRequest	Front에서 입력된 banner 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	입력 로직이 적용된 banner 자료
	 */
    @Override
    public Banner insertBanner(Banner bannerRequest, MultipartFile upload) {
    	Banner banner = null;

    	if (upload != null) {
            // 업로드 파일명
    		bannerRequest.setFileName(upload.getOriginalFilename());

            // 업로드 파일 확장자
    		bannerRequest.setFileExt(bannerRequest.getFileName().substring(bannerRequest.getFileName().lastIndexOf(".") + 1));

        	// 업로드 파일 고유 GUID 입력 및 파일 업로드
        	try {
        		bannerRequest.setGuidName(fileManager.doFileUpload(upload, pathName));
			} catch (Exception e) {
				logger.error("배너 입력 에러 - FileUpload", e.getMessage(), e);
			}
		}

    	try {
            if (bannerRequest.getType() == 1L) {
                bannerRequest.setSeq(bannerRepository.selectMaxSeqByType(bannerRequest.getType()) + 1L);
            } else if(bannerRequest.getType() == 2L) {
                bannerRequest.setSeq(bannerRepository.selectMaxSeqByType(bannerRequest.getType()) + 1L);
            } else if(bannerRequest.getType() == 3L) {
                bannerRequest.setSeq(bannerRepository.selectMaxSeqByType(bannerRequest.getType()) + 1L);
            }

    		banner = bannerRepository.save(bannerRequest);
		} catch (Exception e) {
            logger.error("배너 입력 에러", e.getMessage(), e);
		}

        return banner;
    }

    /**
     * 배너 수정
     * 
     * @param   id      		BANNER_KEY 값
	 * @param 	bannerRequest	Front에서 입력된 banner 자료
	 * @param 	upload			Front에서 입력받은 파일 객체
	 * @return	갱신 로직이 적용된 banner 자료
     */
    @Override
    public Banner updateBanner(Long id, Banner bannerRequest, MultipartFile upload) {
    	Banner bannerResponse = null;
    	Banner banner = super.select(id);

    	if (banner != null) {
        	if (bannerRequest.getTitle() != null && !bannerRequest.getTitle().equals("")) {
        		banner.setTitle(bannerRequest.getTitle());
    		}
    		if (bannerRequest.getHref() != null && !bannerRequest.getHref().equals("")) {
    			banner.setHref(bannerRequest.getHref());
    		}
        	if (bannerRequest.getTarget() != null && !bannerRequest.getTarget().equals("")) {
        		banner.setTarget(bannerRequest.getTarget());
    		}

        	if (bannerRequest.getSeq() != null && !bannerRequest.getSeq().equals(0L)) {
    			banner.setSeq(bannerRequest.getSeq());
    		}
        	if (bannerRequest.getType() != null && !bannerRequest.getType().equals(0L)) {
        		banner.setType(bannerRequest.getType());
    		}

        	// 파일 포함 전체정보 변경
    		if (upload != null) {
    			// 변경된 파일정보
    			banner.setFileName(upload.getOriginalFilename());
    			banner.setFileExt(upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf(".") + 1));

            	// GUID 변경 및 파일 업로드
    			try {
    				banner.setGuidName(fileManager.doFileUpload(upload, pathName));
    			} catch (Exception e) {
    				logger.error("배너 수정 에러 - FileUpload", e.getMessage(), e);
    			}
            }
    	}

    	try {
        	if (banner == null) {
        		throw new Exception();
        	}

        	bannerResponse = bannerRepository.save(banner);
		} catch (Exception e) {
            logger.error("배너 수정 에러", e.getMessage(), e);
		}

        return bannerResponse;
    }

    /**
	 * 배너 삭제
     * 
     * @param   id		BANNER_KEY 값
     * @return  삭제 로직이 적용된 banner 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> bannerMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Banner banner = super.select(id);

    	if (banner != null) {
	        try {
	        	bannerMap.put("bannerKey", banner.getBannerKey());
	        	bannerRepository.delete(banner);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("배너 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	bannerMap.put("deleteYN", deleteYN);
        return bannerMap;
    }

    /**
	 * 배너 공지사항
     * 
     * @return  조회 로직이 적용된 bannerList 자료
     */
    @Override
    public List<Banner> selectBannerTopList() {
        List<Banner> bannerList = bannerRepository.findByTypeOrderBySeqAsc(1L);
        return bannerList;
    }

    /**
     * 배너 공지사항 외
     * 
     * @return  조회 로직이 적용된 bannerList 자료
     */
    @Override
    public List<Banner> selectBannerRemoveTopList() {
        ArrayList<Long> typeList = new ArrayList<Long>();

        typeList.add(2L);
        typeList.add(3L);

        List<Banner> bannerList = bannerRepository.findByTypeInOrderByTypeAscSeqAsc(typeList);

        return bannerList;
    }

    /**
     * 배너 이미지 조회
     * 
     * @param   id      		BANNER_KEY 값
	 * @return	이미지 조회 로직이 적용된 banner 자료
     */
    public Map<String, Object> selectImage(Long id) {
        File file = null;
        InputStreamResource inputStreamResource = null;
        Map<String, Object> imageMap = new HashMap<String, Object>();

        try {
            Banner banner = super.select(id);
            String originalFilename = banner.getFileName();
            String guidName = banner.getGuidName();
            String fileEXT = banner.getFileExt();

            // 파일서버 이전하기 전 임시처리
            fileManager.doFileServerInsert(guidName, originalFilename, pathName, "", "");
            file = new File(pathName + File.separator + guidName);

        	inputStreamResource = new InputStreamResource(new FileInputStream(file));
            imageMap.put("file", file);
            imageMap.put("fileEXT", fileEXT);
            imageMap.put("inputStreamResource", inputStreamResource);
        } catch (FileNotFoundException e) {
            logger.error("배너 이미지 불러오기 에러1 - FileNotFound", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("배너 이미지 불러오기 에러2", e.getMessage(), e);
        }

        return imageMap;
    }
}
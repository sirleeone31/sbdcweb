package com.sbdc.sbdcweb.banner.controller;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.banner.domain.Banner;
import com.sbdc.sbdcweb.banner.service.BannerService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 배너 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-21
 */
@RestController
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BannerRestController extends BaseRestController<Banner, Long> {
    private final BannerService bannerService;

    @Autowired
    public BannerRestController(BannerService bannerService) {
		super(bannerService);
    	this.bannerService = bannerService;
    }

    /**
     * 배너 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/banner")
    public ResponseEntity<List<Banner>> selectBannerList(HttpServletRequest request) {
    	List<Banner> bannerList = bannerService.selectList();

    	if (bannerList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(bannerList);

    }

	/**
	 * 배너 특정 조회
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			BANNER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@GetMapping("/banner/{id}")
    public ResponseEntity<Banner> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Banner banner = bannerService.select(id);

		if (banner == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(banner);
	}

    /**
     * 메인 배너
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/banner-main")
    public ResponseEntity<List<Banner>> selectBannerTopMainList(HttpServletRequest request) {
		List<Banner> bannerList = bannerService.selectBannerTopList();

		if (bannerList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(bannerList);
    }

    /**
	 * 메인 이미지 불러오기
	 * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			BANNER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @GetMapping("/banner/image/{id}")
    public ResponseEntity<InputStreamResource> selectImage(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	String mediaType = null;
    	Map<String, Object> imageMap = bannerService.selectImage(id);

    	if(imageMap.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}

    	File file = (File)imageMap.get("file");
    	String fileEXT = (String)imageMap.get("fileEXT");
    	InputStreamResource inputStreamResource = (InputStreamResource)imageMap.get("inputStreamResource");

    	if (fileEXT.equalsIgnoreCase("png")) {
        	mediaType = "image/png";
		} else if (fileEXT.equalsIgnoreCase("bmp")) {
        	mediaType = "image/bmp";
		} else if (fileEXT.equalsIgnoreCase("gif")) {
        	mediaType = "image/gif";
		} else {
        	mediaType = "image/jpeg";
		}

    	return ResponseEntity.status(HttpStatus.OK)
    			.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + file.getName() + "\"")
    			.header(HttpHeaders.CONTENT_TYPE, mediaType)
    			.contentLength(file.length())
    			.body(inputStreamResource);
    }

}
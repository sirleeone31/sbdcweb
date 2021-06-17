package com.sbdc.sbdcweb.banner.admin.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.banner.domain.Banner;
import com.sbdc.sbdcweb.banner.service.BannerService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 배너 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2018-12-21
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class BannerAdminRestController extends BaseRestController<Banner, Long> {
    private final BannerService bannerService;

    @Autowired
    public BannerAdminRestController(BannerService bannerService) {
		super(bannerService);
    	this.bannerService = bannerService;
    }

    /**
     * 배너 관리자 전체목록 조회
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
	 * 배너 관리자 특정 조회
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
	 * 배너 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	bannerRequest	Front에서 입력된 banner 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/banner")
    public ResponseEntity<Banner> insertBanner(HttpServletRequest request, Banner bannerRequest, MultipartFile upload) {
    	Banner banner = bannerService.insertBanner(bannerRequest, upload);

    	if (banner == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
	 * 배너 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				BANNER_KEY 값
	 * @param 	bannerRequest	Front에서 입력된 banner 자료
	 * @param 	upload			Front에서 입력된 upload 파일 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PatchMapping("/banner/{id}")
    public ResponseEntity<Banner> putNotice(HttpServletRequest request, @PathVariable(value = "id") Long id, Banner bannerRequest, @RequestPart(value = "upload", required = false) MultipartFile upload) {
    	Banner banner = bannerService.updateBanner(id, bannerRequest, upload);

    	if (banner == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
    }

	/**
	 * 배너 관리자 삭제
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				BANNER_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @DeleteMapping("/banner/{id}")
    public ResponseEntity<Banner> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> bannerMap = bannerService.delete(id);
    	boolean deleteYN = (boolean) bannerMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 메인 배너 관리자
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
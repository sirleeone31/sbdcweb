package com.sbdc.sbdcweb.banner.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sbdc.sbdcweb.banner.domain.Banner;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 배너 Service
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date 	: 2018-12-21
 */
public interface BannerService extends BaseService<Banner, Long> {
	public List<Banner> selectList();
	public Banner insertBanner(Banner bannerRequest, MultipartFile upload);
	public Banner updateBanner(Long id, Banner bannerRequest, MultipartFile upload);
	public Map<String, Object> delete(Long id);
	public List<Banner> selectBannerTopList();
	public List<Banner> selectBannerRemoveTopList();
	public Map<String, Object> selectImage(Long id);

}
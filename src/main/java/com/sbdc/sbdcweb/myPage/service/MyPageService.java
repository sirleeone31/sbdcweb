package com.sbdc.sbdcweb.myPage.service;

import com.sbdc.sbdcweb.myPage.domain.response.MyPageAllDto;

import java.util.List;

/**
 * 마이페이지 Service
 *
 * @author  : 서지현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-03
 */
public interface MyPageService {
    List<MyPageAllDto> selectList(Long memberKey);
}
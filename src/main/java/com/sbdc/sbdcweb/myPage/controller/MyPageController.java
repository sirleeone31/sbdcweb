package com.sbdc.sbdcweb.myPage.controller;

import com.sbdc.sbdcweb.myPage.domain.response.MyPageAllDto;
import com.sbdc.sbdcweb.myPage.service.MyPageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 마이페이지 Controller
 *
 * @author  : 서지현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-03
 */
@RestController
@RequestMapping("/mypage")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class MyPageController {
	private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

	/**
     * 마이페이지 내가 쓴 게시물 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
    @GetMapping("/board/{memberKey}")
    public ResponseEntity<List<MyPageAllDto>> selectList(HttpServletRequest request, @PathVariable(value = "memberKey") Long memberKey) {
    	List<MyPageAllDto> boardList = myPageService.selectList(memberKey);

		if (boardList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(boardList);
    }

}
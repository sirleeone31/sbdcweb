package com.sbdc.sbdcweb.myPage.service;

import com.sbdc.sbdcweb.board.domain.Board;
import com.sbdc.sbdcweb.myPage.domain.response.MyPageAllDto;
import com.sbdc.sbdcweb.myPage.repository.MyPageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 마이페이지 ServiceImpl
 *
 * @author  : 서지현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-03
 */
@Service
public class MyPageServiceImpl implements MyPageService{
	private static final Logger logger = LoggerFactory.getLogger(MyPageServiceImpl.class);

	private final MyPageRepository myPageRepository;

    @Autowired
    public MyPageServiceImpl(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

	/**
     * 마이페이지 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 board 자료
     */
    @Override
    public List<MyPageAllDto> selectList(Long memberKey) {
		List<MyPageAllDto> boardMyPageList = new ArrayList<MyPageAllDto>();
		List<Board> boardList = myPageRepository.findByMemberKeyOrderByArticleKeyDesc(memberKey);

		for (Board board : boardList) {
			boardMyPageList.add(new MyPageAllDto(
		    		 board.getArticleKey(),
		    		 board.getBbsCode(),
		    		 board.getArticleNo(),
		    		 board.getSubject(),
		    		 board.getWriter(),
		    		 board.getMemberKey(),
		    		 board.getRegDate()));
		}

    	return boardMyPageList;
    }

}
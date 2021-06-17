package com.sbdc.sbdcweb.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.company.domain.Post;
import com.sbdc.sbdcweb.company.repository.PostRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 직급 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<Post, Long> implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
    	super(postRepository);
    	this.postRepository = postRepository;
    }

    /**
     * 직급 전체목록
     * 
     * @return  조회 로직이 적용된 postList 자료
     */
	@Override
	public List<Post> selectList() {
		List<Post> postList = postRepository.findAllByOrderBySeqAsc();
		return postList;
	}

	/**
	 * 직급 수정
     * 
     * @param   id			POST_KEY 값
     * @param   postRequest	Front에서 입력된 post 자료
     * @return  수정 로직이 적용된 post 자료
     */
	@Override
	public Post update(Long id, Post postRequest) {
		Post post = super.select(id);

		if (post != null) {
			if (postRequest.getPostName() != null && !postRequest.getPostName().equals("")) {
				post.setPostName(postRequest.getPostName());
			}
			if (postRequest.getSeq() != null) {
				post.setSeq(postRequest.getSeq());
			}
		}
		return super.update(id, post);
	}

	/**
	 * 직급 삭제
     * 
     * @param   id	POST_KEY 값
     * @return  삭제 로직이 적용된 post 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> postMap = new HashMap<String, Object>();
        boolean deleteYN = false;
		Post post = super.select(id);

    	if (post != null) {
	        try {
	        	postMap.put("postKey", post.getPostKey());
	        	postRepository.delete(post);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("직급 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	postMap.put("deleteYN", deleteYN);
        return postMap;
    }

}
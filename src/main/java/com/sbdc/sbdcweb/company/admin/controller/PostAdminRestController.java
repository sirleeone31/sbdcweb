package com.sbdc.sbdcweb.company.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.company.domain.Post;
import com.sbdc.sbdcweb.company.service.PostService;
import com.sbdc.sbdcweb.controller.BaseRestController;

/**
 * 직급 관리자 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@RestController
@RequestMapping("/admin/company")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class PostAdminRestController extends BaseRestController<Post, Long> {
    private final PostService postService;

    @Autowired
    public PostAdminRestController(PostService postService) {
    	super(postService);
    	this.postService = postService;
    }

	/**
     * 직급 관리자 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/post")
    public ResponseEntity<List<Post>> selectList(HttpServletRequest request) {
		List<Post> postList = postService.selectList();

		if (postList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(postList);
	}

	/**
     * 직급 관리자 특정 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			POST_KEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/post/{id}")
    public ResponseEntity<Post> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Post post = postService.select(id);

		if (post == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

    /**
	 * 직급 관리자 입력
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	postRequest		Front에서 입력된 post 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PostMapping("/post")
	public ResponseEntity<Post> insert(HttpServletRequest request, Post postRequest) {
		Post post = postService.insert(postRequest);

		if (post == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 직급 관리자 수정
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @param 	id				POST_KEY 값
	 * @param 	postRequest		Front에서 입력된 post 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
	@PatchMapping("/post/{id}")
	public ResponseEntity<Post> update(HttpServletRequest request, @PathVariable(value = "id") Long id, Post postRequest) {
		Post post = postService.update(id, postRequest);

		if (post == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 직급 관리자 삭제
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MENUKEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@DeleteMapping("/post/{id}")
    public ResponseEntity<Post> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = postService.delete(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
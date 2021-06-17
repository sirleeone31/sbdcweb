package com.sbdc.sbdcweb.menu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbdc.sbdcweb.controller.BaseRestController;
import com.sbdc.sbdcweb.menu.domain.Menu;
import com.sbdc.sbdcweb.menu.service.MenuService;

/**
 * 메뉴 Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-31
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class MenuRestController extends BaseRestController<Menu, Long> {
    private final MenuService menuService;

    @Autowired
    public MenuRestController(MenuService menuService) {
    	super(menuService);
    	this.menuService = menuService;
    }

    /**
     * 메뉴 전체목록 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/menu")
    public ResponseEntity<List<Menu>> selectList(HttpServletRequest request) {
		List<Menu> menuList = menuService.selectList();

		if (menuList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

        return ResponseEntity.status(HttpStatus.OK).body(menuList);
	}

    /**
     * 메뉴 특정 조회
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MENUKEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@GetMapping("/menu/{id}")
    public ResponseEntity<Menu> select(HttpServletRequest request, @PathVariable(value = "id") Long id) {
		Menu menu = menuService.select(id);

		if (menu == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(menu);
	}

    /**
     * 메뉴 입력
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	menuRequest	Front에서 입력된 menu 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@PostMapping("/menu")
    public ResponseEntity<Menu> insert(HttpServletRequest request, Menu menuRequest) {
		Menu menu = menuService.insert(menuRequest);

		if (menu == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

    /**
     * 메뉴 수정
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MENUKEY 값
	 * @param 	menuRequest	Front에서 입력된 menu 자료
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@PatchMapping("/menu/{id}")
    public ResponseEntity<Menu> update(HttpServletRequest request, @PathVariable(value = "id") Long id, Menu menuRequest) {
		Menu menu = menuService.update(id, menuRequest);

		if (menu == null) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

    /**
     * 메뉴 삭제
     * 
	 * @param 	request		HttpServletRequest 객체
	 * @param 	id			MENUKEY 값
	 * @return	ResponseEntity(Http 상태값, body 값)
     */
	@DeleteMapping("/menu/{id}")
    public ResponseEntity<Menu> delete(HttpServletRequest request, @PathVariable(value = "id") Long id) {
    	Map<String, Object> deleteMap = menuService.delete(id);
    	boolean deleteYN = (boolean) deleteMap.get("deleteYN");

    	if (!deleteYN) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
package com.sbdc.sbdcweb.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.menu.domain.Menu;
import com.sbdc.sbdcweb.menu.repository.MenuRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 메뉴 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-31
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long>  implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
    	super(menuRepository);
    	this.menuRepository = menuRepository;
    }

    /**
     * 메뉴 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 menu 자료
     */
	@Override
    public List<Menu> selectList() {
    	List<Menu> menuList = menuRepository.findAllByOrderBySeqAscSubSeqAsc();
        return menuList;
    }

	/**
	 * 메뉴 수정
     * 
     * @param   id				MENUKEY 값
     * @param   menuRequest		Front에서 입력된 menu 자료
     * @return  수정 로직이 적용된 menu 자료
     */
	@Override
	public Menu update(Long id, Menu menuRequest) {
		Menu menu = super.select(id);

		if (menu != null) {
			if (menuRequest.getTitle() != null && !menuRequest.getTitle().equals("")) {
				menu.setTitle(menuRequest.getTitle());
			}
			if (menuRequest.getSrc() != null && !menuRequest.getSrc().equals("")) {
				menu.setSrc(menuRequest.getSrc());
			}
		}
		return super.update(id, menu);
	}

	/**
	 * 메뉴 삭제
     * 
     * @param   id	MENUKEY 값
     * @return  삭제 로직이 적용된 menu 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> menuMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Menu menu = super.select(id);

    	if (menu != null) {
	        try {
	        	menuMap.put("menuKey", menu.getMenuKey());
	        	menuRepository.delete(menu);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("메뉴 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	menuMap.put("deleteYN", deleteYN);
        return menuMap;
    }

}
package com.sbdc.sbdcweb.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.admin.domain.Role;
import com.sbdc.sbdcweb.admin.repository.AdminRoleRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 관리자 권한 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<Role, Long> implements AdminRoleService {
	private static final Logger logger = LoggerFactory.getLogger(AdminRoleServiceImpl.class);

    private final AdminRoleRepository adminRoleRepository;
	private final String code = "role";

	@Autowired
    public AdminRoleServiceImpl(AdminRoleRepository adminRoleRepository) {
    	super(adminRoleRepository);
        this.adminRoleRepository = adminRoleRepository;
    }

    /**
     * 관리자 권한 코드 설정 Getter
     */
    @Override
	public String getCode() {
		return code;
	}

    /**
	 * 관리자 권한 수정
     * 
     * @param   id             	ROLE_KEY 값
     * @param   roleRequest    	Front에서 입력된 info 자료
     * @return  갱신 로직이 적용된 role 자료
     */
    @Override
	public Role updateRole(Long id, Role roleRequest) {
    	Role role = super.select(id);

    	if (role != null) {
        	if (roleRequest.getCode() != null && !roleRequest.getCode().equals("")) {
        		role.setCode(roleRequest.getCode());
    		}
    		if (roleRequest.getAuthName() != null && !roleRequest.getAuthName().equals("")) {
    			role.setAuthName(roleRequest.getAuthName());
    		}
    	}

    	return super.update(id, role);
	}

    /**
	 * 관리자 권한 삭제
     * 
     * @param   id		ROLE_KEY 값
     * @return  삭제 로직이 적용된 role 자료
     */
    @Override
	public Map<String, Object> deleteRole(Long id) {
        Map<String, Object> roleMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Role role = super.select(id);

        if (role != null) {
            try {
            	roleMap.put("roleKey", role.getRoleKey());
            	adminRoleRepository.delete(role);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error("권한 삭제 에러", e.getMessage(), e);
        	}
        }

        roleMap.put("deleteYN", deleteYN);
        return roleMap;
	}

}
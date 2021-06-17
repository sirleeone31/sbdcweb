package com.sbdc.sbdcweb.admin.service;

import java.util.Map;

import com.sbdc.sbdcweb.admin.domain.Role;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 관리자 권한 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
public interface AdminRoleService extends BaseService<Role, Long> {
	public String getCode();

	public Role updateRole(Long id, Role roleRequest);
	public Map<String, Object> deleteRole(Long id);
}
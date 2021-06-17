package com.sbdc.sbdcweb.admin.service;

import java.util.List;
import java.util.Map;

import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.admin.domain.AdminAllDto;
import com.sbdc.sbdcweb.admin.domain.request.ChangePwForm;
import com.sbdc.sbdcweb.admin.domain.request.LoginForm;
import com.sbdc.sbdcweb.admin.domain.request.SignUpForm;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 관리자 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
public interface AdminService extends BaseService<Admin, Long> {
	public String getCode();

	public List<AdminAllDto> selectAdminList();
	public Admin selectAdminId(String adminId);
	public Admin signUpAdmin(SignUpForm signUpRequest, String ip);
	public Admin updateAdmin(Long id, SignUpForm signUpRequest);
	public Map<String, Object> deleteAdmin(Long id);
	public Map<String, Object> signInAdmin(LoginForm loginRequest);

   	public String updateDamoPassword(String password);
	public void updateAdminLog(String adminId, String ip);
//   	public boolean updateAdminRoleAuth(String adminId);
	public Map<String, Object> updateAdminPassword(Long id, ChangePwForm changeRequest);

}
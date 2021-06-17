package com.sbdc.sbdcweb.admin.service;

import com.sbdc.sbdcweb.admin.domain.AdminLog;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 관리자 로그 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
public interface AdminLogService extends BaseService<AdminLog, Long> {
    public AdminLog insertAdminLog(String adminId, String code, String action, String ment, String ip);
}
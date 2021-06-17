package com.sbdc.sbdcweb.admin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.admin.domain.AdminLog;
import com.sbdc.sbdcweb.admin.repository.AdminLogRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 관리자 로그 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-31
 */
@Service
public class AdminLogServiceImpl extends BaseServiceImpl<AdminLog, Long> implements AdminLogService {
	private static final Logger logger = LoggerFactory.getLogger(AdminRoleServiceImpl.class);

    private final AdminLogRepository adminLogRepository;

    @Autowired
    public AdminLogServiceImpl(AdminLogRepository adminLogRepository) {
    	super(adminLogRepository);
    	this.adminLogRepository = adminLogRepository;
    }

	/**
     * 권한코드 전체목록 조회
     * 
	 * @return	조회 로직이 적용된 Log 자료
     */
    @Override
	public List<AdminLog> selectList() {
        List<AdminLog> adminLogList = adminLogRepository.findAllByOrderByLogKeyDesc();

        int j = 0;

        for (int i = adminLogList.size(); i > 0; i--) {
        	adminLogList.get(j).setNum(i);
            j++;
        }
    	return adminLogList;
	}

    /**
	 * 권한코드 입력
	 * 
	 * @param 	adminId		관리자 계정
	 * @param 	code		코드 값
	 * @param 	action		관리자 행위
	 * @param 	ment		처리 자료
	 * @param 	ip			처리 IP
	 * @return	입력 로직이 적용된 Log 자료
	 */
    @Override
    public AdminLog insertAdminLog(String adminId, String code, String action, String ment, String ip) {
		AdminLog adminLog = null;

		try {
	        adminLog = new AdminLog(adminId, code, action, ment, ip);
    		adminLogRepository.save(adminLog);
        } catch (Exception e) {
            logger.error("권한코드 입력 에러", e.getMessage(), e);
        }

        return adminLog;
    }

}
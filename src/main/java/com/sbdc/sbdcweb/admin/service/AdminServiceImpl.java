package com.sbdc.sbdcweb.admin.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.admin.domain.AdminAllDto;
import com.sbdc.sbdcweb.admin.domain.Role;
import com.sbdc.sbdcweb.admin.domain.request.ChangePwForm;
import com.sbdc.sbdcweb.admin.domain.request.LoginForm;
import com.sbdc.sbdcweb.admin.domain.request.SignUpForm;
import com.sbdc.sbdcweb.admin.repository.AdminRepository;
import com.sbdc.sbdcweb.admin.repository.AdminRoleRepository;
import com.sbdc.sbdcweb.config.ApplicationProperties;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 관리자 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final JwtProvider jwtProvider;
	private final ApplicationProperties applicationProperties;
	private final String code = "admin";

	@Autowired
    public AdminServiceImpl(AuthenticationManager authenticationManager,
    						AdminRepository adminRepository,
    						AdminRoleRepository adminRoleRepository,
    						JwtProvider jwtProvider,
    						ApplicationProperties applicationProperties) {
    	super(adminRepository);
        this.authenticationManager = authenticationManager;
        this.adminRepository = adminRepository;
        this.adminRoleRepository = adminRoleRepository;
        this.jwtProvider = jwtProvider;
        this.applicationProperties = applicationProperties;
    }

	/**
     * 관리자 코드 설정 Getter
     */
    @Override
	public String getCode() {
		return code;
	}

    /**
     * 관리자 전체목록 조회
     * 
     * @return  조회 로직이 적용된 adminList 자료
     */
    @Override
    public List<AdminAllDto> selectAdminList() {
    	List<AdminAllDto> adminList = adminRepository.selectAdminAllDtoOrderByRegDateDesc();
        return adminList;
    }

	/**
	 * 관리자 adminId 조건으로 특정 조회
	 * 
	 * @param 	adminId		ADMIN_ID
	 * @return	조회 로직이 적용된 admin 자료
	 */
    @Override
    public Admin selectAdminId(String adminId) {
    	Admin admin = null;

    	try {
    		admin = adminRepository.findByUsername(adminId);
        } catch (Exception e) {
            logger.error("관리자 adminId 조건으로 특정 조회 에러", e.getMessage(), e);
        }

    	return admin;
    }

	/**
	 * 관리자 입력
	 * 
	 * @param 	signUpRequest	Front에서 입력된 signUp 자료
	 * @param 	ip				ip 자료
	 * @return	입력 로직이 적용된 admin 자료
	 */
    @Override
    public Admin signUpAdmin(SignUpForm signUpRequest, String ip) {
        Admin admin = null;

        try {
            if (adminRepository.existsByUsername(signUpRequest.getUsername())) {
            	throw new Exception();
            }

        	Set<String> strRoles = signUpRequest.getRoles();
        	Set<Role> roles = new HashSet<>();

    		for (String role : strRoles) {
    			Role roleName = adminRoleRepository.findByCode(role);
    			if (roleName == null) {
            		throw new Exception();
    			}
    			roles.add(roleName);
    		}

    		if (applicationProperties.getDataServer().equals("10.0.9.21")) {
        		admin = new Admin(signUpRequest.getUsername(), updateDamoPassword(signUpRequest.getPassword()), 
        				signUpRequest.getName(), signUpRequest.getEnableOuterLogin(), 
        				ip, signUpRequest.getAllowedIp());
    		} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
        		admin = new Admin(signUpRequest.getUsername(), signUpRequest.getPassword(), 
        				signUpRequest.getName(), signUpRequest.getEnableOuterLogin(), 
        				ip, signUpRequest.getAllowedIp());
    		}

            admin.setRoles(roles);

            // 차후 실서버 연동 시 검토 - 컨트롤러, applicationProperties 사용
//            saveAdminAuth(signUpRequest.getUsername());
        } catch (Exception e) {
            logger.error("관리자 입력 에러", e.getMessage(), e);
        }

        return super.insert(admin);
    }

    /**
	 * 관리자 수정
	 * 
	 * @param 	id				ADMIN_KEY
	 * @param 	signUpRequest	Front에서 입력된 signUp 자료
	 * @return	수정 로직이 적용된 admin 자료
	 */
    @Override
    public Admin updateAdmin(Long id, SignUpForm signUpRequest) {
    	Admin admin = super.select(id);

    	if (admin != null) {
    		if (signUpRequest.getRoles() != null) {
        		Set<String> strRoles = signUpRequest.getRoles();
                Set<Role> roles = new HashSet<>();

        		for (String role : strRoles) {
        			Role roleName = adminRoleRepository.findByCode(role);
        			if (roleName != null) {
            			roles.add(roleName);       				
        			}
        		}

                admin.setRoles(roles);
    		}
    		if (signUpRequest.getUsername() != null && !signUpRequest.getUsername().equals("")) {
        		admin.setUsername(signUpRequest.getUsername());
    		}
    		if (signUpRequest.getName() != null && !signUpRequest.getName().equals("")) {
        		admin.setName(signUpRequest.getName());
    		}
    		if (signUpRequest.getEnableOuterLogin() != null && !signUpRequest.getEnableOuterLogin().equals("")) {
    			admin.setEnableOuterLogin(signUpRequest.getEnableOuterLogin());
    		}
    		if (signUpRequest.getAllowedIp() != null && !signUpRequest.getAllowedIp().equals("")) {
    			admin.setAllowedIp(signUpRequest.getAllowedIp());
    		}

    		if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().equals("")) {
        		if (applicationProperties.getDataServer().equals("10.0.9.21")) {
	        		admin.setPassword(updateDamoPassword(signUpRequest.getPassword()));
	        	} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
	        		admin.setPassword(signUpRequest.getPassword());
	    		}
    		}

    		admin.setModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    	}

    	return super.update(id, admin);
    }

    /**
	 * 관리자 삭제
	 * 
	 * @param 	id		ADMIN_KEY
	 * @return	삭제 로직이 적용된 admin 자료
	 */
    @Override
    public Map<String, Object> deleteAdmin(Long id) {
        Map<String, Object> adminMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Admin admin = super.select(id);

    	if (admin != null) {
	        try {
	        	adminMap.put("adminKey", admin.getAdminKey());
	        	adminRepository.delete(admin);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("관리자 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	adminMap.put("deleteYN", deleteYN);
        return adminMap;
    }

    /**
     * 관리자 로그인
     *
     * @param   loginRequest 	Front에서 입력된 signIn 자료
     * @return  adminMap 로그인 시 Jwt 토큰값, userDetails(회원 ID, 회원 권한) 값, ADMIN_KEY 값 리턴
     */
    @Override
    public Map<String, Object> signInAdmin(LoginForm loginRequest) {
    	boolean signInYN = false;
    	Map<String, Object> adminMap = new HashMap<String, Object>();
		Authentication authentication = null;

		try {
    		if (applicationProperties.getDataServer().equals("10.0.9.21")) {
                authentication = authenticationManager.authenticate(
                		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), updateDamoPassword(loginRequest.getPassword())));    			
    		} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
                authentication = authenticationManager.authenticate(
                		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    		}

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            adminMap.put("jwt", jwt);
            adminMap.put("userDetails", userDetails);
            adminMap.put("adminKey", selectAdminId(userDetails.getUsername()).getAdminKey());
            signInYN = true;
    	} catch (Exception e) {
    		logger.error("관리자 로그인 에러", e.getMessage(), e);
    	}

    	adminMap.put("signInYN", signInYN);
    	return adminMap;
    }

    /**
     * Damo 암호화 함수 적용 password
     *
     * @param   password	비밀번호
	 * @return	Damo 암호화 함수 적용 후 password
     */
    @Override
   	public String updateDamoPassword(String password) {
        return adminRepository.selectDamoPasswordByPassword(password);
    }

    /**
     * 관리자 로그인 시 Admin 최근접속날짜, 최근접속IP 수정
     *
     * @param   adminId   접속 ID
     * @param   ip        접속 IP
     */
    @Override
    public void updateAdminLog(String adminId, String ip) {
        try {
        	String lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            Admin admin = adminRepository.findByUsername(adminId);
            adminRepository.updateLastLoginLastIpByAdminKey(lastLogin, ip, admin.getAdminKey());
		} catch (Exception e) {
            logger.error("관리자 로그인 시 Admin 최근접속날짜, 최근접속IP 수정 에러", e.getMessage(), e);
		}
    }

   	/**
     * 관리자 입력 시 권한 수정
     * 운영서버에서 권한테이블에 adminKey가 업데이트가 되지 않는 현상으로 인해 정적으로 update 진행
     *
     * @param	adminId		AdminId
	 * @return	update 여부
     */
/*
    @Override
    public boolean updateAdminRoleAuth(String adminId) {
    	boolean updateYN = false;

    	try {
            Long adminKey = adminRepository.selectAdminKeyByAdminId(adminId);
            Integer updateRow = adminRepository.updateNewAdminKeyByOldAdminKey(adminKey, 0L);

            if (updateRow > 0) {
                updateYN = true;
            }
		} catch (Exception e) {
            logger.error("관리자 입력 시 권한 수정 에러", e.getMessage(), e);
		}

        return updateYN;
    }
*/

    /**
	 * 관리자 PASSWORD 변경
	 * 
	 * @param 	id				ADMIN_KEY 값
	 * @param 	changeRequest	Front에서 입력된 change 자료
	 * @return	변경 여부 및 상태 값
	 */
    @Override
    public Map<String, Object> updateAdminPassword(Long id, ChangePwForm changeRequest) {
        String changeMessage = "N";
    	boolean changeYN = false;
        Map<String, Object> adminMap = new HashMap<String, Object>();

    	try {
    		Admin admin = super.select(id);

        	if (admin != null) {

        		// 기존 암호와 등록한 암호가 같지 않은 경우
        		if (!admin.getPassword().equals(changeRequest.getOldPassword())) {
	                changeMessage = "O";
	        		throw new Exception();
	        	}

        		// 새로 입력한 암호와 새로 입력한 확인 암호가 같지 않은 경우	
	        	if (!changeRequest.getNewPassword().equals(changeRequest.getConfirmPassword())) {
	        		changeMessage = "C";
	        		throw new Exception();
	        	}

	        	if (changeRequest.getNewPassword() != null && !changeRequest.getNewPassword().equals("")) {
	        		if (applicationProperties.getDataServer().equals("10.0.9.21")) {
		        		admin.setPassword(updateDamoPassword(changeRequest.getNewPassword()));
		        	} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
		        		admin.setPassword(changeRequest.getNewPassword());
		    		}
                }

	        	admin.setModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
	            adminMap.put("adminKey", admin.getAdminKey());

	            adminRepository.save(admin);
	            changeMessage = "Y";
	    		changeYN = true;
            }
    	} catch (Exception e) {
            logger.error("관리자 PASSWORD 변경 에러", e.getMessage(), e);
		}

    	adminMap.put("changeMessage", changeMessage);
        adminMap.put("changeYN", changeYN);

        return adminMap;
    }

}
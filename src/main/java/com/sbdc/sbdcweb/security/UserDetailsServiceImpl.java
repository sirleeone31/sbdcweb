package com.sbdc.sbdcweb.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbdc.sbdcweb.admin.domain.Admin;
import com.sbdc.sbdcweb.admin.repository.AdminRepository;
import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.member.repository.MemberRepository;

/**
 * UserDetailsServiceImpl
 * UserDetailsService 구현 클래스
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(AdminRepository adminRepository, MemberRepository memberRepository) {
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * loadUserByUsername 함수
     * 
     * @param username 	username(ID) 값
     * @return UserDetailsImpl.build를 통해 생성된 값
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Base64.Decoder decoder = Base64.getDecoder();
//        byte[] decoded = decoder.decode(adminId);

    	UserDetails userDetails = null;

    	Admin admin = adminRepository.findByUsername(username);
    	Member member = memberRepository.findByUsername(username);

    	if (admin == null && member == null) {
    		logger.info("loadUserByUsername 에러");
    		throw new UsernameNotFoundException(username);
    	}

    	if (admin != null) {
    		userDetails = UserDetailsImpl.build(admin);
    	} else if (member != null) {
    		userDetails = UserDetailsImpl.build(member);    		
    	}

    	return userDetails;
    }

}
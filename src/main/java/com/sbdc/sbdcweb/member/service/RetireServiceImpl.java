package com.sbdc.sbdcweb.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.member.domain.Retire;
import com.sbdc.sbdcweb.member.repository.RetireRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 회원탈퇴이력 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Service
public class RetireServiceImpl extends BaseServiceImpl<Retire, Long> implements RetireService {
	private final RetireRepository retireRepository;

	@Autowired
    public RetireServiceImpl(RetireRepository retireRepository) {
    	super(retireRepository);
    	this.retireRepository = retireRepository;
    }

    /**
     * 회원탈퇴이력 전체목록 조회(관리자)
     * 
     * @return  조회 로직이 적용된 retireList 자료
     */
    @Override
	public List<Retire> selectList() {
		List<Retire> retireList = retireRepository.findAllByOrderByRetireKeyDesc();
		return retireList;
	}

	/**
	 * 회원탈퇴이력 입력
	 * 
	 * @param 	memberType		MEMBER_TYPE
	 * @param 	companyName		COMPANY_NAME
	 * @param 	memberKey		MEMBER_KEY
	 * @param 	retireComment	RETIRE_COMMENT
	 * @param 	ip				IP
	 * @return	입력 로직이 적용된 retire 자료
	 */
    @Override
    public Retire insertRetireLog(Long memberType, String companyName, Long memberKey, String retireComment, String ip) {
    	Retire retire = new Retire(memberType, companyName, memberKey, retireComment, ip);
        return super.insert(retire);
    }

}
package com.sbdc.sbdcweb.member.service;

import java.util.List;
import java.util.Map;

import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.member.domain.MemberDto;
import com.sbdc.sbdcweb.member.domain.request.ChangeForm;
import com.sbdc.sbdcweb.member.domain.request.DeleteForm;
import com.sbdc.sbdcweb.member.domain.request.LoginForm;
import com.sbdc.sbdcweb.member.domain.request.SignUpForm;
import com.sbdc.sbdcweb.service.BaseService;

/**
 * 회원 Service
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
public interface MemberService extends BaseService<Member, Long> {
	public String getCode();

	// 관리자 회원관리 영역
	public List<MemberDto> selectMemberList();
	public MemberDto selectMember(Long id);
	public Member updateMember(Long id);
	public Member updateMember(Long id, ChangeForm deleteRequest);
	public Map<String, Object> deleteMember(Long id);
	public Map<String, Object> deleteMember(Long id, DeleteForm deleteRequest);

	// 사용자 회원 영역
	public Member signUpMember(SignUpForm signUpRequest, String ip);
	public Map<String, Object> signInMember(LoginForm loginRequest);
	public void updateMember(Member member, String passwordEnc);
	public void updateMemberAuth(String memberId);
	public void updateMemberLog(String memberId, String ip);
	public Map<String, Object> updateMemberPassword(String password, String certCi);

	public Boolean existsMemberId(String memberId);
	public Boolean existsRegNo(String regNo);
	public Boolean existsEmail(String email);
	public Boolean existsDI(String certDupInfo);
	public Boolean adultCheck(String sBirthDate);
	public Boolean validationRegNo(String regNo);
	public String selectMemberId(String sConnInfo);

	public String updateDamoPassword(String password);

}
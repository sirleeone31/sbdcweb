package com.sbdc.sbdcweb.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.sbdc.sbdcweb.common.CommonUtils;
import com.sbdc.sbdcweb.config.ApplicationProperties;
import com.sbdc.sbdcweb.member.domain.Member;
import com.sbdc.sbdcweb.member.domain.MemberDto;
import com.sbdc.sbdcweb.member.domain.Role;
import com.sbdc.sbdcweb.member.domain.request.ChangeForm;
import com.sbdc.sbdcweb.member.domain.request.DeleteForm;
import com.sbdc.sbdcweb.member.domain.request.LoginForm;
import com.sbdc.sbdcweb.member.domain.request.SignUpForm;
import com.sbdc.sbdcweb.member.repository.MemberRepository;
import com.sbdc.sbdcweb.member.repository.MemberRoleRepository;
import com.sbdc.sbdcweb.security.jwt.JwtProvider;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 회원 ServiceImpl
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	private final AuthenticationManager authenticationManager;
	private final MemberRepository memberRepository;
	private final MemberRoleRepository memberRoleRepository;
    private final JwtProvider jwtProvider;
    private final ApplicationProperties applicationProperties;
	private final CommonUtils commonUtils;
    private final String code = "member";

	@Autowired
    public MemberServiceImpl(AuthenticationManager authenticationManager,
    						 MemberRepository memberRepository,
    						 MemberRoleRepository memberRoleRepository,
    						 JwtProvider jwtProvider,
    						 ApplicationProperties applicationProperties,
                             CommonUtils commonUtils) {
    	super(memberRepository);
        this.authenticationManager = authenticationManager;
    	this.memberRepository = memberRepository;
    	this.memberRoleRepository = memberRoleRepository;
        this.jwtProvider = jwtProvider;
        this.applicationProperties = applicationProperties;
    	this.commonUtils = commonUtils;
    }

    /**
     * 회원 코드 설정 Getter
     */
    @Override
	public String getCode() {
		return code;
	}

    /**
     * 회원 전체목록 조회(관리자)
     * 
     * @return  조회 로직이 적용된 memberList 자료
     */
    @Override
	public List<MemberDto> selectMemberList() {
		List<MemberDto> memberDtoList = new ArrayList<MemberDto>();
		List<Member> memberList = super.selectList();

		for (Member member : memberList) {
			memberDtoList.add(new MemberDto(
					member.getMemberKey(),
					member.getMemberType(),
					member.getUsername(),
					member.getRegNo()));
		}

		return memberDtoList;
	}

    /**
	 * 회원 특정 조회(관리자)
	 * 
	 * @param 	id	MEMBER_KEY
	 * @return	조회 로직이 적용된 member 자료
	 */
    @Override
	public MemberDto selectMember(Long id) {
    	Member member = super.select(id);
    	MemberDto memberDto = null;

    	if (member != null) {
    		memberDto = new MemberDto(
    				member.getMemberKey(),
    				member.getMemberType(),
    				member.getUsername(),
		    		member.getRegNo());
		}

		return memberDto;
	}

    /**
	 * 회원 수정(관리자) - 암호변경
	 * 
	 * @param 	id	MEMBER_KEY
	 * @return	수정 로직이 적용된 member 자료
	 */
    @Override
	public Member updateMember(Long id) {
    	Member member = super.select(id);

		if (member != null) {
			if (applicationProperties.getDataServer().equals("10.0.9.21")) {
				member.setPassword(updateDamoPassword(commonUtils.randomPassword()));
			} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
				member.setPassword(commonUtils.randomPassword());
			}
		}

		return super.update(id, member);
	}

    /**
	 * 회원 수정(사용자)
	 * 
	 * @param 	id	MEMBER_KEY
	 * @return	수정 로직이 적용된 member 자료
	 */
    @Override
	public Member updateMember(Long id, ChangeForm changeRequest) {
    	Member member = super.select(id);

		if (member != null) {
			if (changeRequest.getRegNo() != null && !changeRequest.getRegNo().equals("")) {
				member.setRegNo(changeRequest.getRegNo());
			}
			if (changeRequest.getCompanyName() != null && !changeRequest.getCompanyName().equals("")) {
				member.setCompanyName(changeRequest.getCompanyName());
			}
			if (changeRequest.getCeoName() != null && !changeRequest.getCeoName().equals("")) {
				member.setCeoName(changeRequest.getCeoName());
			}
			if (changeRequest.getManagerName() != null && !changeRequest.getManagerName().equals("")) {
				member.setManagerName(changeRequest.getManagerName());
			}
			if (changeRequest.getManagerPost() != null && !changeRequest.getManagerPost().equals("")) {
				member.setManagerPost(changeRequest.getManagerPost());
			}
			if (changeRequest.getEmail() != null && !changeRequest.getEmail().equals("")) {
				member.setEmail(changeRequest.getEmail());
			}
			if (changeRequest.getTel1() != null && !changeRequest.getTel1().equals("")) {
				member.setTel1(changeRequest.getTel1());
			}
			if (changeRequest.getTel2() != null && !changeRequest.getTel2().equals("")) {
				member.setTel2(changeRequest.getTel2());
			}
			if (changeRequest.getTel3() != null && !changeRequest.getTel3().equals("")) {
				member.setTel3(changeRequest.getTel3());
			}
			if (changeRequest.getCel1() != null && !changeRequest.getCel1().equals("")) {
				member.setCel1(changeRequest.getCel1());
			}
			if (changeRequest.getCel2() != null && !changeRequest.getCel2().equals("")) {
				member.setCel2(changeRequest.getCel2());
			}
			if (changeRequest.getCel3() != null && !changeRequest.getCel3().equals("")) {
				member.setCel3(changeRequest.getCel3());
			}
		}

		return super.update(id, member);
	}

    /**
	 * 회원 삭제(관리자)
	 * 
	 * @param 	id	MEMBER_KEY 값
	 * @return	삭제 로직이 적용된 member 자료
	 */
    @Override
    public Map<String, Object> deleteMember(Long id) {
        Map<String, Object> memberMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Member member = super.select(id);

        if (member != null) {
            try {
            	memberMap.put("memberKey", member.getMemberKey());
            	memberRepository.delete(member);
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error(code + " 회원 삭제 에러", e.getMessage(), e);
        	}
        }

        memberMap.put("deleteYN", deleteYN);
        return memberMap;
    }

    /**
	 * 회원 삭제(사용자)
	 * 
	 * @param 	id	MEMBER_KEY 값
	 * @return	삭제 로직이 적용된 member 자료
	 */
    @Override
    public Map<String, Object> deleteMember(Long id, DeleteForm deleteRequest) {
        Map<String, Object> memberMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        String retire = "Y";
        Long retireReason = 0L;
    	String retireDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Member member = super.select(id);

        if (member != null) {
            try {
	        	memberMap.put("memberKey", member.getMemberKey());
            	memberMap.put("memberType", member.getMemberType());

	        	if (member.getMemberType() == 1L) {
		        	memberMap.put("companyName", member.getCompanyName());
	        	} else if (member.getMemberType() == 2L) {
		        	memberMap.put("companyName", "개인회원");
	        	}

            	memberRepository.updateRetireByMemberKey(retire, retireReason, deleteRequest.getRetireComment(), retireDate, member.getMemberKey());
        		deleteYN = true;
        	} catch (Exception e) {
        		logger.error(code + " 회원 삭제 에러", e.getMessage(), e);
        	}
        }

        memberMap.put("deleteYN", deleteYN);
        return memberMap;
    }

	/**
	 * 회원가입(사용자)
	 * 
	 * @param 	signUpRequest	Front에서 입력된 signUp 자료
	 * @param 	ip				ip 자료
	 * @return	입력 로직이 적용된 member 자료
	 */
    @Override
    public Member signUpMember(SignUpForm signUpRequest, String ip) {
    	Member member = null;

        try {
            if (memberRepository.existsByUsername(signUpRequest.getUsername())) {
            	throw new Exception();
            }

        	Set<String> strRoles = signUpRequest.getRoles();
        	Set<Role> roles = new HashSet<>();

    		for (String role : strRoles) {
    			Role roleName = memberRoleRepository.findByCode(role);
    			if (roleName == null) {
            		throw new Exception();
    			}
    			roles.add(roleName);
    		}

    		if (applicationProperties.getDataServer().equals("10.0.9.21")) {
    			member = new Member(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), 
    					updateDamoPassword(signUpRequest.getPassword()), signUpRequest.getMemberType(), 
                        signUpRequest.getCertDupInfo(), signUpRequest.getCertCi(), 
                        signUpRequest.getCompanyName(), signUpRequest.getCeoName(), signUpRequest.getManagerName(), signUpRequest.getManagerPost(),
                        signUpRequest.getTel1(), signUpRequest.getTel2(), signUpRequest.getTel3(),
                        signUpRequest.getCel1(), signUpRequest.getCel2(), signUpRequest.getCel3(),
        				ip);
    		} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
    			member = new Member(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), 
    					signUpRequest.getPassword(), signUpRequest.getMemberType(), 
                        signUpRequest.getCertDupInfo(), signUpRequest.getCertCi(), 
                        signUpRequest.getCompanyName(), signUpRequest.getCeoName(), signUpRequest.getManagerName(), signUpRequest.getManagerPost(),
                        signUpRequest.getTel1(), signUpRequest.getTel2(), signUpRequest.getTel3(),
                        signUpRequest.getCel1(), signUpRequest.getCel2(), signUpRequest.getCel3(),
        				ip);
    		}

    		member.setRoles(roles);

            // 차후 실서버 연동 시 검토 - 컨트롤러, applicationProperties 사용
//            saveAdminAuth(signUpRequest.getUsername());
        } catch (Exception e) {
            logger.error("회원가입 에러", e.getMessage(), e);
        }

        return super.insert(member);
    }

    /**
     * 회원 로그인(사용자)
     * 
     * @param   loginRequest 	Front에서 입력된 signIn 자료
     * @return  memberMap 로그인 시 Jwt 토큰값, userDetails(회원 ID, 회원 권한) 값, MEMBER_KEY 값 리턴
     */
    @Override
    public Map<String, Object> signInMember(LoginForm loginRequest) {
    	boolean signInYN = false;
    	Map<String, Object> memberMap = new HashMap<String, Object>();
        Authentication authentication = null;

        try {
			if (applicationProperties.getDataServer().equals("10.0.9.21")) {
				// 실 DB
				authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), memberRepository.selectDamoPasswordByPassword(loginRequest.getPassword())));
			} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
				// 테스트 DB
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			}

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtProvider.generateJwtToken(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 로그인 시 TB_AUTH에 memberKey, JWT, username 저장
//        AuthMember authMember = new AuthMember(memberRepository.selectMemberKeyByMemberId(loginRequest.getUsername()), jwt, loginRequest.getUsername());
//        authMemberRepository.save(authMember);
        // !--로그인 시 TB_AUTH에 memberKey, JWT, username 저장

	        memberMap.put("jwt", jwt);
	        memberMap.put("userDetails", userDetails);
	        signInYN = true;
        } catch (Exception e) {
    		logger.error("회원 로그인 에러", e.getMessage(), e);
    	}

        memberMap.put("signInYN", signInYN);
        return memberMap;
    }

    // 명칭 변경 사용?
    /**
     * Damo 암호화 함수 적용 후 insert
     *
     * @param member      Member 객체
     * @param passwordEnc 인코딩 할 암호값
     */
    @Override
    public void updateMember(Member member, String passwordEnc) {
        member.setPassword(memberRepository.selectDamoPasswordByPassword(passwordEnc));
        memberRepository.save(member);
    }

    // 명칭 변경
    /**
     * 권한 수정
     * 운영서버에서 권한테이블에 memberKey가 업데이트가 되지 않는 현상으로 인해 정적으로 update
     *
     * @param memberId   MemberId 또는  RegNo
     */
    @Override
    public void updateMemberAuth(String memberId) {
        Long newMemberKey = 0L;

//      if (memberType == 1L) { // 기업회원
//      	newMemberKey = memberRepository.selectMemberKeyByRegNo(memberId);
//      } else if (memberType == 2L) { // 일반회원
//      	newMemberKey = memberRepository.selectMemberKeyByMemberId(memberId);
//      }

        try {
            newMemberKey = memberRepository.selectMemberKeyByMemberId(memberId);        	
            memberRepository.updateNewAdminKeyByOldAdminKey(newMemberKey, 0L);
        } catch(Exception e) {
            logger.error("권한 수정 에러", e.getMessage(), e);
        }
    }

    /**
     * 회원 로그인 시 Member 최근접속날짜, 최근접속IP 수정
     *
     * @param memberId  Username
     * @param ip 		접속 IP
     */
    @Override
   	public void updateMemberLog(String memberId, String ip) {
        try {
        	String lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        	Member member = memberRepository.findByUsername(memberId);
            memberRepository.updateLastLoginLastIpByMemberKey(lastLogin, ip, member.getMemberKey());
		} catch (Exception e) {
            logger.error("회원 로그인 시 Member 최근접속날짜, 최근접속IP 수정 에러", e.getMessage(), e);
		}
    }

    /**
     * 비번 수정(사용자)
     *
     * @param 	password 변경할 PW
     * @param 	certCi   휴대폰 인증 CI 값
     * @return 	changeYN 변경 여부 체크 후 true, false 값 리턴
     */
    @Override
    public Map<String, Object> updateMemberPassword(String password, String certCi) {
        String changeMessage = "N";
    	boolean changeYN = false;
        Integer changeRow = 0;
    	Map<String, Object> memberMap = new HashMap<String, Object>();

    	try {
			if (applicationProperties.getDataServer().equals("10.0.9.21")) {
		        // 실 DB
		        String passwordEnc = memberRepository.selectDamoPasswordByPassword(password);
				changeRow = memberRepository.updateMemberPw(passwordEnc, certCi);
			} else if (applicationProperties.getDataServer().equals("127.0.0.1")) {
				// 테스트 DB
		        changeRow = memberRepository.updateMemberPw(password, certCi);
			}
	
	        if (changeRow > 0) {
	            changeMessage = "Y";
	        	changeYN = true;
	        }
    	} catch (Exception e) {
            logger.error("관리자 PASSWORD 변경 에러", e.getMessage(), e);
		}

    	memberMap.put("changeMessage", changeMessage);
    	memberMap.put("changeYN", changeYN);

    	return memberMap;
    }

    /**
     * Member ID 존재 여부 조회
     *
     * @param 	memberId 	Member_ID 값
     * @return 	존재하면 true, 존재하지 않으면 false
     */
    @Override
    public Boolean existsMemberId(String memberId) {
        boolean exists = memberRepository.existsByUsername(memberId);
        return exists;
    }

    /**
     * 사업자번호 존재 여부 조회
     *
     * @param 	regNo 	REGNO 값
     * @return 	존재하면 true, 존재하지 않으면 false
     */
    @Override
    public Boolean existsRegNo(String regNo) {
        boolean exists = memberRepository.existsByRegNo(regNo);
        return exists;
    }

    /**
     * email 존재 여부 조회
     *
     * @param 	email 	EMAIL 값
     * @return 	존재하면 true, 존재하지 않으면 false
     */
    @Override
    public Boolean existsEmail(String email) {
        boolean exists = memberRepository.existsByEmail(email);
        return exists;
    }

    /**
     * Nice 인증 DI 존재 여부 조회
     *
     * @param 	certDupInfo Nice 인증 DI 값
     * @return 	존재하면 true, 존재하지 않으면 false
     */
    @Override
    public Boolean existsDI(String certDupInfo) {
        boolean exists = memberRepository.existsByCertDupInfo(certDupInfo);
        return exists;
    }

    /**
     * 14세 미만 조회
     * 
     * @param   sBirthDate	Nice 휴대폰 인증을 통해서 넘어온 생년월일
     * @return  14세 이상인 경우 true, 미만인 경우 false
     */
    @Override
    public Boolean adultCheck(String sBirthDate) {
        boolean adultYN = commonUtils.adultCheck(sBirthDate);
        return adultYN;
    }

    /**
     * 사업자번호 유효성 체크
     *
     * @param 	regNo RegNo 값
     * @return  유효성 확인된 경우 true, 확인되지 않은 경우 false
     */
    @Override
    public Boolean validationRegNo(String regNo) {
        boolean validYN = commonUtils.checkCompNumber(regNo);
        return validYN;
    }

    /**
     * certCi 조건으로 memberId 조회
     * 
     * @param 	sConnInfo 	nice 인증 CI 값
     * @return 	조회 로직이 적용된 memberId
     */
    @Override
    public String selectMemberId(String sConnInfo) {
        String memberId = memberRepository.selectMemberIdByCertCi(sConnInfo);
        return memberId;
    }

    /**
     * Damo 암호화 함수 적용 password
     *
     * @param   password	비밀번호
	 * @return	Damo 암호화 함수 적용 후 password
     */
    @Override
   	public String updateDamoPassword(String password) {
        return memberRepository.selectDamoPasswordByPassword(password);
    }

}
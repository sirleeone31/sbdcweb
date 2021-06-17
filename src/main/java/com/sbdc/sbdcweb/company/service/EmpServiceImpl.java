package com.sbdc.sbdcweb.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.company.domain.Emp;
import com.sbdc.sbdcweb.company.domain.response.EmpAllDto;
import com.sbdc.sbdcweb.company.repository.EmpRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 직원 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 */
@Service
public class EmpServiceImpl extends BaseServiceImpl<Emp, Long> implements EmpService {
    private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);

	private final EmpRepository empRepository;

    @Autowired
    public EmpServiceImpl(EmpRepository empRepository) {
    	super(empRepository);
    	this.empRepository = empRepository;
    }

    /**
     * 직원 전체목록 조회
     * 
     * @return  조회 로직이 적용된 empList 자료
     */
	@Override
	public List<Emp> selectList() {
		List<Emp> empList = empRepository.findAllByOrderByPostKeyAscEmpNoAsc();
		return empList;
	}

    /**
     * 직원 전체목록 일부 컬럼 조회
     * 
     * @return  조회 로직이 적용된 empList 자료
     */
	@Override
	public List<EmpAllDto> selectEmpList() {
    	List<EmpAllDto> empList = empRepository.selectEmpAllDtoOrderBySeqAscEmpNoAsc();
    	return empList;
    }

    /**
     * 직원 전체목록 일부 컬럼 조회 - 부서별 검색(deptKey로 검색)
     * 
     * @return  조회 로직이 적용된 empList 자료
     */
	@Override
	public List<EmpAllDto> selectEmpByDeptKeyList(Long deptKey) {
		List<EmpAllDto> empList = null;

//		전체직원 검색 : deptKey 0(전체)
		if (deptKey == 0L) {
			empList = empRepository.selectEmpAllDtoOrderBySeqAscEmpNoAsc();
		} else {
			empList = empRepository.selectEmpAllDtoByDeptKeyOrderBySeqAscEmpNoAsc(deptKey);
		}

    	return empList;
    }

    /**
     * 직원 전체목록 일부 컬럼 조회 - 이름 검색(empName로 검색)
     * 
     * @return  조회 로직이 적용된 empList 자료
     */
	@Override
	public List<EmpAllDto> selectEmpByEmpNameList(String empName) {
		List<EmpAllDto> empList = null;

//		전체직원 검색 : empName all(전체)
		if (empName.equals("all")) {
			empList = empRepository.selectEmpAllDtoOrderBySeqAscEmpNoAsc();
		} else {
			empList = empRepository.selectEmpAllDtoByEmpNameOrderBySeqAscEmpNoAsc(empName);
		}

    	return empList;
    }

    /**
     * 직원 전체목록 일부 컬럼 조회 - 부서별, 이름 검색(deptKey, empName로 검색)
     * 
     * @return  조회 로직이 적용된 empList 자료
     */
	@Override
	public List<EmpAllDto> selectEmpByDeptKeyAndEmpNameList(Long deptKey, String empName) {
		List<EmpAllDto> empList = null;

		// 1. 전체직원 검색 : deptKey 0(전체), 이름 all(전체)
		if (deptKey == 0L && empName.equals("all")) {
			empList = empRepository.selectEmpAllDtoOrderBySeqAscEmpNoAsc();
		}
		// 2. 전체 직원 이름 검색 : deptKey 0(전체), 이름으로만 검색
		else if (deptKey == 0L && !empName.equals("all")) {
			empList = empRepository.selectEmpAllDtoByEmpNameOrderBySeqAscEmpNoAsc(empName);
		}
		// 3. 부서별 직원 검색 : deptKey 존재, 이름 all(전체)
		else if (deptKey != 0L && empName.equals("all")) {
			empList = empRepository.selectEmpAllDtoByDeptKeyOrderBySeqAscEmpNoAsc(deptKey);
		}
		// 4. 부서별 직원 이름 검색 : deptKey, 이름 존재, deptKey + 이름 검색
		else if (deptKey != 0L && !empName.equals("all")) {
			empList = empRepository.selectEmpAllDtoByDeptKeyAndEmpNameOrderBySeqAscEmpNoAsc(deptKey, empName);
		}

    	return empList;
    }

    /**
     * 직원 특정 일부 컬럼 조회
     * 
     * @param   id			EMP_KEY 값
     * @return  조회 로직이 적용된 emp 자료
     */
	public EmpAllDto selectEmp(Long id) {
		EmpAllDto emp = null; 

    	try {
    		emp = empRepository.selectEmpAllDtoByEmpKey(id);
    	} catch (NoSuchElementException e) {
            logger.error("직원 특정 일부 컬럼 조회 조회 에러", e.getMessage(), e);
		}

		return emp;
	}

	/**
	 * 직원 수정
     * 
     * @param   id			EMP_KEY 값
     * @param   empRequest	Front에서 입력된 emp 자료
     * @return  수정 로직이 적용된 emp 자료
     */
	@Override
	public Emp update(Long id, Emp empRequest) {
		Emp emp = super.select(id);

		if (emp != null) {
			if (empRequest.getEmpName() != null && !empRequest.getEmpName().equals("")) {
				emp.setEmpName(empRequest.getEmpName());
			}
			if (empRequest.getEmpNo() != null && !empRequest.getEmpNo().equals("")) {
				emp.setEmpNo(empRequest.getEmpNo());
			}
			if (empRequest.getEmpTel() != null && !empRequest.getEmpTel().equals("")) {
				emp.setEmpTel(empRequest.getEmpTel());
			}
			if (empRequest.getEmpKey() != null) {
				emp.setEmpKey(empRequest.getEmpKey());
			}
			if (empRequest.getPostKey() != null) {
				emp.setPostKey(empRequest.getPostKey());
			}
		}
		return super.update(id, emp);
	}

	/**
	 * 직원 삭제
     * 
     * @param   id	EMP_KEY 값
     * @return  삭제 로직이 적용된 emp 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> empMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Emp emp = super.select(id);

    	if (emp != null) {
	        try {
	        	empMap.put("empKey", emp.getEmpKey());
	        	empRepository.delete(emp);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("직원 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	empMap.put("deleteYN", deleteYN);
        return empMap;
    }

}
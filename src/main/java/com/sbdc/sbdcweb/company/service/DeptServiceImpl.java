package com.sbdc.sbdcweb.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.company.domain.Dept;
import com.sbdc.sbdcweb.company.domain.response.DeptOneDto;
import com.sbdc.sbdcweb.company.repository.DeptRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 부서 ServiceImpl 작성
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-16
 * 
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept, Long> implements DeptService {
    private static final Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);

    private final DeptRepository deptRepository;

    @Autowired
    public DeptServiceImpl(DeptRepository deptRepository) {
    	super(deptRepository);
    	this.deptRepository = deptRepository;
    }

    /**
     * 부서 전체목록
     * 
     * @return  조회 로직이 적용된 deptList 자료
     */
	@Override
	public List<Dept> selectList() {
		List<Dept> deptList = deptRepository.findAllByOrderByDeptKeyAsc();
		return deptList;
	}

    /**
     * 부서 특정 조회
     * 
     * @return  조회 로직이 적용된 deptList 자료
     */
	@Override
	public DeptOneDto selectDept(Long id) {
		Dept dept = super.select(id);
		DeptOneDto deptOne = null;

		if (dept != null) {
    		deptOne = new DeptOneDto(
    				dept.getDeptKey(),
    				dept.getDeptName(),
    				dept.getParentKey(),
    				dept.getDeptTel(),
    				dept.getDeptFax(),
    				dept.getDeptLeader(),
    				dept.getDeptTask(),
    				dept.getDeptType());

    		deptOne.setDeptTask("<p>" + dept.getDeptTask().replaceAll("╊", "</p><p>") + "</p>");
		}

    	return deptOne;
	}

	/**
	 * 부서 수정
     * 
     * @param   id			DEPT_KEY 값
     * @param   deptRequest	Front에서 입력된 dept 자료
     * @return  수정 로직이 적용된 dept 자료
     */
	@Override
	public Dept update(Long id, Dept deptRequest) {
		Dept dept = super.select(id);

		if (dept != null) {
			if (deptRequest.getDeptName() != null && !deptRequest.getDeptName().equals("")) {
				dept.setDeptName(deptRequest.getDeptName());
			}
			if (deptRequest.getParentKey() != null) {
				dept.setParentKey(deptRequest.getParentKey());
			}
			if (deptRequest.getDeptTel() != null && !deptRequest.getDeptTel().equals("")) {
				dept.setDeptTel(deptRequest.getDeptTel());
			}
			if (deptRequest.getDeptFax() != null && !deptRequest.getDeptFax().equals("")) {
				dept.setDeptFax(deptRequest.getDeptFax());
			}

			if (deptRequest.getDeptLeader() != null) {
				dept.setDeptLeader(deptRequest.getDeptLeader());
			}
			if (deptRequest.getDeptTask() != null && !deptRequest.getDeptTask().equals("")) {
				dept.setDeptTask(deptRequest.getDeptTask());
			}
			if (deptRequest.getDeptType() != null) {
				dept.setDeptType(deptRequest.getDeptType());
			}
		}
		return super.update(id, dept);
	}

	/**
	 * 부서 삭제
     * 
     * @param   id	DEPT_KEY 값
     * @return  삭제 로직이 적용된 dept 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> deptMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Dept dept = super.select(id);

    	if (dept != null) {
	        try {
	        	deptMap.put("deptKey", dept.getDeptKey());
	        	deptRepository.delete(dept);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("부서 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	deptMap.put("deleteYN", deleteYN);
        return deptMap;
    }

}
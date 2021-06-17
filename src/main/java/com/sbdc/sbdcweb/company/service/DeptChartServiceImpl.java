package com.sbdc.sbdcweb.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.company.domain.DeptChart;
import com.sbdc.sbdcweb.company.repository.DeptChartRepository;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 조직도 ServiceImpl 
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-01-15
 */
@Service
public class DeptChartServiceImpl extends BaseServiceImpl<DeptChart, Long> implements DeptChartService {
    private final DeptChartRepository deptChartRepository;

    @Autowired
    public DeptChartServiceImpl(DeptChartRepository deptChartRepository) {
    	super(deptChartRepository);
    	this.deptChartRepository = deptChartRepository;
    }

    /**
     * 조직도 전체목록
     * 
     * @return  조회 로직이 적용된 deptChartList 자료
     */
	@Override
	public List<DeptChart> selectList() {
		List<DeptChart> deptChartList = deptChartRepository.findAllByOrderBySortAsc();
		return deptChartList;
	}

}
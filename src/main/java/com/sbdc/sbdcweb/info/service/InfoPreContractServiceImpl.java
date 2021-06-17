package com.sbdc.sbdcweb.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.info.domain.InfoPreContract;
import com.sbdc.sbdcweb.info.domain.response.InfoPreContractAllDto;
import com.sbdc.sbdcweb.info.domain.response.InfoPreContractOneDto;
import com.sbdc.sbdcweb.info.repository.InfoPreContractRepository;

/**
 * 2017년 이전 계약현황 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-11
 */
@Service
public class InfoPreContractServiceImpl implements InfoPreContractService {
	private static final Logger logger = LoggerFactory.getLogger(InfoPreContractServiceImpl.class);

	private final InfoPreContractRepository infoPreContractRepository;
    private final String infoCode = "precontract";

    @Autowired
	public InfoPreContractServiceImpl(InfoPreContractRepository infoPreContractRepository) {
		this.infoPreContractRepository = infoPreContractRepository;
	}

    /**
     * 이전 계약현황 코드 설정 Getter
     */
    @Override
	public String getInfoCode() {
		return infoCode;
	}

    /**
     * 이전 계약현황 전체목록 조회
     * 
     * @return  조회 로직이 적용된 preContractList 자료
     */
	@Override
	public List<InfoPreContractAllDto> selectInfoList() {
		List<InfoPreContractAllDto> infoAllList = new ArrayList<InfoPreContractAllDto>();
		List<InfoPreContract> infoList = infoPreContractRepository.findAllByOrderByContractKeyDesc();

		if (infoList != null) {
			for (InfoPreContract infoPreContract : infoList) {
				infoAllList.add(new InfoPreContractAllDto(
						infoPreContract.getContractKey(),
						infoPreContract.getContractName(),
						infoPreContract.getRequestDept(),
						infoPreContract.getStartDate(),
						infoPreContract.getEndDate(),
						infoPreContract.getCompany()));
			}
	
			int j = 0;
	
			for (int i = infoAllList.size(); i > 0; i--) {
				infoAllList.get(j).setNum(i);
				j++;
			}
		}

		return infoAllList;
	}

	/**
	 * 이전 계약현황 특정 게시물 조회
	 * 
	 * @param 	id	CONTRACT_KEY 값
	 * @return	조회 로직이 적용된 preContract 자료
	 */
	@Override
	public InfoPreContractOneDto selectInfo(Long id) {
		InfoPreContractOneDto infoOnePreContract = null;

		try {
			InfoPreContract infoPreContract = infoPreContractRepository.findById(id).get();
	
			if (infoPreContract != null) {
				infoOnePreContract = new InfoPreContractOneDto(
						infoPreContract.getContractKey(),
						infoPreContract.getContractNo(),
						infoPreContract.getContractName(),
						infoPreContract.getGubun(),
						infoPreContract.getAmount(),
						infoPreContract.getHasVat(),
						infoPreContract.getStartDate(),
						infoPreContract.getEndDate(),
			    		infoPreContract.getCompany(),
			    		infoPreContract.getCeo(),
			    		infoPreContract.getContractCause());
	
			}
    	} catch (NoSuchElementException e) {
            logger.error("이전 계약현황 특정 게시물 조회 에러", e.getMessage(), e);
		}

		return infoOnePreContract;
	}

}
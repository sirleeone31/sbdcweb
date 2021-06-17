package com.sbdc.sbdcweb.info.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.info.domain.Ethic;
import com.sbdc.sbdcweb.info.repository.EthicRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 윤리경영 관리 ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-09-22
 */
@Service
@Primary
public class EthicServiceImpl extends InfoServiceImpl<Ethic, Long> implements EthicService {
    private static final Logger logger = LoggerFactory.getLogger(EthicServiceImpl.class);

    private final EthicRepository ethicRepository;
    private final String infoCode = "ethic";

    @Autowired
    public EthicServiceImpl(EthicRepository ethicRepository) {
        super(ethicRepository);
        super.setInfoCode(infoCode);
        this.ethicRepository = ethicRepository;
    }

    /**
     * 윤리경영 관리 전체목록 조회
     * 
     * @return  조회 로직이 적용된 infoList 자료
     */
    @Override
    public List<Ethic> selectInfoList() {
        List<Ethic> infoList = super.selectInfoList();

        int j = 0;

        for (int i = infoList.size(); i > 0; i--) {
            infoList.get(j).setNum(i);
            j++;
        }

        return infoList;
    }

    /**
	 * 윤리경영 관리 게시물 수정
     * 
     * @param   id              ETHIC_KEY 값
     * @param   ethicRequest    Front에서 입력된 ethic 자료
     * @return  갱신 로직이 적용된 ethic 자료
     */
    @Override
    public Ethic update(Long id, Ethic infoRequest) {
    	Ethic ethic = super.select(id);

    	if (ethic != null) {
			if (infoRequest.getTitle() != null && !infoRequest.getTitle().equals("")) {
				ethic.setTitle(infoRequest.getTitle());
			}
			if (infoRequest.getYyyymm() != null && !infoRequest.getYyyymm().equals("")) {
				ethic.setYyyymm(infoRequest.getYyyymm());
			}
			if (infoRequest.getLink() != null && !infoRequest.getLink().equals("")) {
				ethic.setLink(infoRequest.getLink());
			}
    	}

		return super.update(id, ethic);
    }

    /**
	 * 윤리경영 관리 게시물 삭제
     * 
     * @param   id          ETHIC_KEY 값
     * @return  삭제 로직이 적용된 ethic 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        boolean deleteYN = false;
    	Ethic ethic = super.select(id);

    	if (ethic != null) {
	        try {
	        	infoMap.put("infoKey", ethic.getInfoKey());
	        	ethicRepository.delete(ethic);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error(infoCode + " 게시물 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	infoMap.put("deleteYN", deleteYN);
        return infoMap;
    }

}
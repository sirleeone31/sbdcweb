package com.sbdc.sbdcweb.civil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.civil.domain.Report;
import com.sbdc.sbdcweb.civil.repository.CivilReportRepository;
import com.sbdc.sbdcweb.mail.service.MailingService;
import com.sbdc.sbdcweb.service.BaseServiceImpl;

/**
 * 민원 및 불공정 행정 - 고충처리 + 부정부패 ServiceImpl
 * 
 * @author  : 김도현
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-02-13
 */
@Service
public class CivilReportServiceImpl extends BaseServiceImpl<Report, Long> implements CivilReportService {
    private static final Logger logger = LoggerFactory.getLogger(CivilReportServiceImpl.class);

    private final CivilReportRepository civilReportRepository;
    private final MailingService mailingService;
    private final String infoCode = "civilreport";

    @Autowired
	public CivilReportServiceImpl(CivilReportRepository civilReportRepository, MailingService mailingService) {
    	super(civilReportRepository);
		this.civilReportRepository = civilReportRepository;
		this.mailingService = mailingService;
	}

    /**
     * 민원 및 불공정 행정 코드 설정 Getter
     */
    @Override
	public String getInfoCode() {
		return infoCode;
	}

    /**
     * 민원 및 불공정 행정 전체목록(고충처리 / 부정부패 => reportType으로 구분)
     * 
     * @return  조회 로직이 적용된 reportList 자료
     */
	@Override
	public List<Report> selectList() {
		List<Report> reportList = civilReportRepository.findAllByOrderByReportKeyDesc();
		return reportList;
	}

    /**
     * 민원 및 불공정 행정 입력
     * 
     * @param   reportRequest	Front에서 입력된 report 자료
     * @return  입력 로직이 적용된 report 자료
     */
	@Override
	public Report insert(Report reportRequest) {
		Report report = null;

    	try {
    		report = civilReportRepository.save(reportRequest);
	        // 담당자 메일 발송
    		mailingService.sendMail("civil", report.getName(), report.getSubject());
    	} catch (Exception e) {
            logger.error("민원 및 불공정 행정 입력 에러", e.getMessage(), e);
		}

        return report;
	}

	/**
	 * 민원 및 불공정 행정 게시물 수정
     * 
     * @param   id				REPORT_KEY 값
     * @param   reportRequest	Front에서 입력된 report 자료
     * @return  수정 로직이 적용된 report 자료
     */
	@Override
	public Report update(Long id, Report reportRequest) {
		Report report = super.select(id);

		if (report != null) {
			if (reportRequest.getName() != null && !reportRequest.getName().equals("")) {
				report.setName(reportRequest.getName());
			}
			if (reportRequest.getTel() != null && !reportRequest.getTel().equals("")) {
				report.setTel(reportRequest.getTel());
			}
			if (reportRequest.getCel() != null && !reportRequest.getCel().equals("")) {
				report.setCel(reportRequest.getCel());
			}
			if (reportRequest.getEmail() != null && !reportRequest.getEmail().equals("")) {
				report.setEmail(reportRequest.getEmail());
			}
			if (reportRequest.getSubject() != null && !reportRequest.getSubject().equals("")) {
				report.setSubject(reportRequest.getSubject());
			}
			if (reportRequest.getContents() != null && !reportRequest.getContents().equals("")) {
				report.setContents(reportRequest.getContents());
			}
		}
		return super.update(id, report);
	}

	/**
	 * 민원 및 불공정 행정 게시물 삭제
     * 
     * @param   id	REPORT_KEY 값
     * @return  삭제 로직이 적용된 report 자료
     */
    @Override
    public Map<String, Object> delete(Long id) {
        Map<String, Object> reportMap = new HashMap<String, Object>();
        boolean deleteYN = false;
        Report report = super.select(id);

    	if (report != null) {
	        try {
	        	reportMap.put("reportKey", report.getReportKey());
	        	reportMap.put("memberNo", report.getMemberNo());
	        	civilReportRepository.delete(report);
	    		deleteYN = true;
	    	} catch (Exception e) {
	    		logger.error("민원 및 불공정 행정 게시물 삭제 에러", e.getMessage(), e);
	    	}
    	}

    	reportMap.put("deleteYN", deleteYN);
        return reportMap;
    }

    /**
	 * 서버에 접속한 사용자 IP 설정
	 * 
	 * @param 	ip				ip 값
	 * @param 	boardRequest	ip 값을 설정할 board 자료
	 */
    @Override
	public void insertIp(String ip, Report reportRequest) {
    	reportRequest.setIp(ip);
	}

}
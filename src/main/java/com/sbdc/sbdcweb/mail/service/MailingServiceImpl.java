package com.sbdc.sbdcweb.mail.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sbdc.sbdcweb.board.domain.BoardConsultType;
import com.sbdc.sbdcweb.board.repository.BoardConsultTypeRepository;
import com.sbdc.sbdcweb.mail.domain.CivilEmail;
import com.sbdc.sbdcweb.mail.domain.EqnaEmail;
import com.sbdc.sbdcweb.mail.domain.QnaEmail;
import com.sbdc.sbdcweb.mail.repository.CivilEmailRepository;
import com.sbdc.sbdcweb.mail.repository.EqnaEmailRepository;
import com.sbdc.sbdcweb.mail.repository.QnaEmailRepository;

/**
 * Mailing ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@Service
@Primary
public class MailingServiceImpl implements MailingService {
	private static final Logger logger = LoggerFactory.getLogger(MailingServiceImpl.class);

	private JavaMailSender javaMailSender;
	private CivilEmailRepository civilEmailRepository;
	private EqnaEmailRepository eqnaEmailRepository;
	private QnaEmailRepository qnaEmailRepository;
	private BoardConsultTypeRepository boardConsultTypeRepository;

	@Autowired
	public MailingServiceImpl(JavaMailSender javaMailSender, 
			CivilEmailRepository civilEmailRepository, 
			EqnaEmailRepository eqnaEmailRepository, 
			QnaEmailRepository qnaEmailRepository) {
		this.javaMailSender = javaMailSender;
		this.civilEmailRepository = civilEmailRepository;
		this.eqnaEmailRepository = eqnaEmailRepository;
		this.qnaEmailRepository = qnaEmailRepository;
	}

	/**
     * Mail 발송
     * 
	 * @param 	email		회원 email
	 * @param 	password	변경된 비밀번호
     */
	@Override
	public Boolean sendMail(String email, String password) throws MailException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
		String mailSubject = null;
		String text = null;
		boolean successYN = false;

		try {
			mailSubject = "중소기업유통센터 홈페이지 비밀번호 변경";
			text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>비밀번호 변경이 완료되었습니다.</h4></div>"
					+ "<div style='text-align:center'>비밀번호 : " + password + "</div><br>"
					+ "<div style='text-align:center'><b><a href=https://www.sbdc.or.kr target='_blank' title='중소기업유통센터 홈페이지 바로가기'>중소기업유통센터 홈페이지 바로가기 클릭</a></b></div><br><br><br>";

			mimeMessageHelper.setFrom("gwadmin@sbdc.or.kr", "홈페이지 관리자");
			mimeMessageHelper.setBcc(email);
			mimeMessageHelper.setSubject(mailSubject);
			mimeMessageHelper.setText(text, true);
			javaMailSender.send(mimeMessage);
			successYN = true;
		} catch (MailException e) {
            logger.error("Mail 발송 에러 1", e.getMessage(), e);
		} catch (Exception e) {
            logger.error("Mail 발송 에러 2", e.getMessage(), e);
		}
		return successYN;
	}

	/**
     * Mail 발송
     * 
	 * @param 	bbsCode	게시판 코드
	 * @param 	writer	게시물 작성자
	 * @param 	subject	게시물 제목
     */
	@Override
	public Boolean sendMail(String bbsCode, String writer, String subject) throws MailException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
		List<String> stringList = new ArrayList<String>();
		String[] array = null;
		String mailSubject = null;
		String text = null;
		boolean successYN = false;

		try {
			if (bbsCode.equals("eqna")) {
				List<EqnaEmail> eqnaEmailList = eqnaEmailRepository.findAll();

				if (!eqnaEmailList.isEmpty()) {
					for (EqnaEmail eqnaEmail : eqnaEmailList) {
						stringList.add(eqnaEmail.getEmail());
					}
					mailSubject = "기관 홈페이지 [윤리경영 Q&A] 게시판의 새로운 게시물이 접수되었습니다.";
					text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>게시판에 새로운 게시물이 접수되었습니다.</h4></div>"
							+ "<div style='text-align:center'>작성자 : " + writer + "</div>"
							+ "<div style='text-align:center'>제목 : " + subject + "</div><br>"
							+ "<div style='text-align:center'><p>관리자 페이지 접속 후</p><p><strong>[윤리경영 Q&A 관리]</strong> 메뉴에서</p><p>접수된 게시물 처리를 진행하시기 바랍니다.</p></div><br>"
							+ "<div style='text-align:center'><b><a href=http://admin.sbdc.or.kr/_admin target='_blank' title='관리자 페이지 바로가기'>관리자 페이지 바로가기 클릭</a></b></div><br><br><br>";
				}
			} else if (bbsCode.equals("qna")) {
				List<QnaEmail> qnaEmailList = qnaEmailRepository.findAll();

				if (!qnaEmailList.isEmpty()) {
					for (QnaEmail qnaEmail : qnaEmailList) {
						stringList.add(qnaEmail.getEmail());
					}
					mailSubject = "기관 홈페이지 [고객상담] 게시판의 새로운 게시물이 접수되었습니다.";
					text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>게시판에 새로운 게시물이 접수되었습니다.</h4></div>"
							+ "<div style='text-align:center'>작성자 : " + writer + "</div>"
							+ "<div style='text-align:center'>제목 : " + subject + "</div><br>"
							+ "<div style='text-align:center'><p>관리자 페이지 접속 후</p><p><strong>[고객상담 관리]</strong> 메뉴에서</p><p>접수된 게시물 처리를 진행하시기 바랍니다.</p></div><br>"
							+ "<div style='text-align:center'><b><a href=http://admin.sbdc.or.kr/_admin target='_blank' title='관리자 페이지 바로가기'>관리자 페이지 바로가기 클릭</a></b></div><br><br><br>";
				}
			} else if (bbsCode.equals("inside")) {
				List<CivilEmail> civilEmailList = civilEmailRepository.findAll();

				if (!civilEmailList.isEmpty()) {
					for (CivilEmail civilEmail : civilEmailList) {
						stringList.add(civilEmail.getEmail());
					}
					mailSubject = "기관 홈페이지 [내부 신고센터] 게시판의 새로운 게시물이 접수되었습니다.";
					text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>게시판에 새로운 게시물이 접수되었습니다.</h4></div>"
							+ "<div style='text-align:center'>작성자 : " + writer + "</div>"
							+ "<div style='text-align:center'>제목 : " + subject + "</div><br>"
							+ "<div style='text-align:center'><p>관리자 페이지 접속 후</p><p><strong>[내부신고센터]</strong> 메뉴에서</p><p>접수된 게시물 처리를 진행하시기 바랍니다.</p></div><br>"
							+ "<div style='text-align:center'><b><a href=http://admin.sbdc.or.kr/_admin target='_blank' title='관리자 페이지 바로가기'>관리자 페이지 바로가기 클릭</a></b></div><br><br><br>";
				}
			} else if (bbsCode.equals("civil")) {
				List<CivilEmail> civilEmailList = civilEmailRepository.findAll();

				if (!civilEmailList.isEmpty()) {
					for (CivilEmail civilEmail : civilEmailList) {
						stringList.add(civilEmail.getEmail());
					}
					mailSubject = "기관 홈페이지 [고충처리] 게시판의 새로운 게시물이 접수되었습니다.";
					text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>게시판에 새로운 게시물이 접수되었습니다.</h4></div>"
							+ "<div style='text-align:center'>작성자 : " + writer + "</div>"
							+ "<div style='text-align:center'>제목 : " + subject + "</div><br>"
							+ "<div style='text-align:center'><p>관리자 페이지 접속 후</p><p><strong>[고충처리 관리]</strong> 메뉴에서</p><p>접수된 게시물 처리를 진행하시기 바랍니다.</p></div><br>"
							+ "<div style='text-align:center'><b><a href=http://admin.sbdc.or.kr/_admin target='_blank' title='관리자 페이지 바로가기'>관리자 페이지 바로가기 클릭</a></b></div><br><br><br>";
				}
			}

			if (!stringList.isEmpty()) {
				array = stringList.toArray(new String[stringList.size()]);
				mimeMessageHelper.setFrom("gwadmin@sbdc.or.kr", "홈페이지 관리자");
				mimeMessageHelper.setBcc(array);
				mimeMessageHelper.setSubject(mailSubject);
				mimeMessageHelper.setText(text, true);
				javaMailSender.send(mimeMessage);
				successYN = true;
			}
		} catch (MailException e) {
            logger.error("Mail 발송 에러 1", e.getMessage(), e);
		} catch (Exception e) {
            logger.error("Mail 발송 에러 2", e.getMessage(), e);
		}
		
		return successYN;
	}

	/**
     * 입점 및 판매상담 Mail 발송
     * 
	 * @param 	bbsCode		게시판 코드
	 * @param 	writer		게시물 작성자
	 * @param 	subject		게시물 제목
	 * @param 	consultType	입점 및 판매상담 카테고리
     */
	@Override
	public Boolean sendMail(String bbsCode, String writer, String subject, String consultType) throws MailException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
		List<String> stringList = new ArrayList<String>();
		String[] array = null;
		String mailSubject = null;
		String text = null;
		boolean successYN = false;

		try {
			if (bbsCode.equals("consult")) {
				List<BoardConsultType> boardConsultTypeList = boardConsultTypeRepository.findByName(consultType);

				if (!boardConsultTypeList.isEmpty()) {
					for (BoardConsultType boardConsultType : boardConsultTypeList) {
						stringList.add(boardConsultType.getEmail());
					}
					mailSubject = "기관 홈페이지 [입점 및 판매상담] 게시판의 새로운 게시물이 접수되었습니다.";
					text = "<div><h4 style='color:#6399D7;font-weight:bold;text-align:center'>게시판에 새로운 게시물이 접수되었습니다.</h4></div>"
							+ "<div style='text-align:center'>작성자 : " + writer + "</div>"
							+ "<div style='text-align:center'>제목 : " + subject + "</div>"
							+ "<div style='text-align:center'>입점 및 판매상담 카테고리 : " + consultType + "</div><br>"
							+ "<div style='text-align:center'><p>관리자 페이지 접속 후</p><p><strong>[입점 및 판매상담 관리]</strong> 메뉴에서</p><p>접수된 게시물 처리를 진행하시기 바랍니다.</p></div><br>"
							+ "<div style='text-align:center'><b><a href=http://admin.sbdc.or.kr/_admin target='_blank' title='관리자 페이지 바로가기'>관리자 페이지 바로가기 클릭</a></b></div><br><br><br>";
				}
			}

			if (!stringList.isEmpty()) {
				array = stringList.toArray(new String[stringList.size()]);
				mimeMessageHelper.setFrom("gwadmin@sbdc.or.kr", "홈페이지 관리자");
				mimeMessageHelper.setBcc(array);
				mimeMessageHelper.setSubject(mailSubject);
				mimeMessageHelper.setText(text, true);
				javaMailSender.send(mimeMessage);
				successYN = true;
			}
		} catch (MailException e) {
            logger.error("Mail 발송 에러 1", e.getMessage(), e);
		} catch (Exception e) {
            logger.error("Mail 발송 에러 2", e.getMessage(), e);
		}

		return successYN;
	}

}
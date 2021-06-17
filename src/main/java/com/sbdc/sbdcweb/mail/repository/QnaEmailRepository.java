package com.sbdc.sbdcweb.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.mail.domain.QnaEmail;

/**
 * 고객상담관리 알림메일발송관리 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@RepositoryRestResource
public interface QnaEmailRepository extends JpaRepository<QnaEmail, Long>, EmailRepository<QnaEmail, Long> {}
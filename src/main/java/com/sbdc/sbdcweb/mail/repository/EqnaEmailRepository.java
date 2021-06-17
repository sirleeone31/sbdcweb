package com.sbdc.sbdcweb.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sbdc.sbdcweb.mail.domain.EqnaEmail;

/**
 * 윤리경영관리 알림메일발송관리 Repository
 *  
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@RepositoryRestResource
public interface EqnaEmailRepository extends JpaRepository<EqnaEmail, Long>, EmailRepository<EqnaEmail, Long> {}
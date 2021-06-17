package com.sbdc.sbdcweb.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sbdc.sbdcweb.repository.BaseRepository;

/**
 * Email Repository
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-10-22
 */
@NoRepositoryBean
public interface EmailRepository<T, ID> extends JpaRepository<T, ID>, BaseRepository<T, ID> {}
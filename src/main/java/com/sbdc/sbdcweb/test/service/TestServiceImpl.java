package com.sbdc.sbdcweb.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Test ServiceImpl
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-08-29
 */
@Service
public class TestServiceImpl implements TestService {
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	public TestServiceImpl() {
	}

}
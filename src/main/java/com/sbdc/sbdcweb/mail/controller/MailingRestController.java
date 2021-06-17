package com.sbdc.sbdcweb.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbdc.sbdcweb.mail.service.MailingService;

/**
 * Mailing Controller
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-08
 */
@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}")
public class MailingRestController {
	private final MailingService mailingService;

	@Autowired
    public MailingRestController(MailingService mailingService) {
    	this.mailingService = mailingService;
    }

	/**
	 * 
	 * @return
	 */
	@GetMapping("/send")
	public ResponseEntity<?> sendMail() {
		Boolean successYN = false;
//		successYN = mailingService.sendMail("sirlee@sbdc.or.kr", "");
		if (successYN == false) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(successYN);
	}

}
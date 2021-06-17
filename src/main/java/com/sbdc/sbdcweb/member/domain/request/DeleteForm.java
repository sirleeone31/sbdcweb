package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

/**
 * ChangeForm Domain
 * 회원탈퇴
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class DeleteForm {
	private String retireComment;
}
package com.sbdc.sbdcweb.member.domain.request;

import lombok.Data;

/**
 * FindForm Domain
 * ID 찾기
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-06-10
 */
@Data
public class FindForm {
    private String username;
    private String regNo;
}
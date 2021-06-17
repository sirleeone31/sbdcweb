package com.sbdc.sbdcweb.admin.domain.response;

import lombok.Data;

/**
 * ResponseMessage
 * 프런트로 Response 보낼 Message
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-07-30
 */
@Data
public class ResponseMessage {
    private String message;
    private boolean yn;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(boolean yn) {
        this.yn = yn;
    }

    public ResponseMessage(String message, boolean yn) {
        this.message = message;
        this.yn = yn;
    }
}
package com.sbdc.sbdcweb.security.jwt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * JWT Controller
 *
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-11-01
 */
@CrossOrigin(origins = "${sbdc.app.frontServerAPI}", maxAge = 3600)
@RestController
@RequestMapping("/")
public class JwtRestController {
	private final JwtProvider jwtProvider;

	@Autowired
    public JwtRestController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
	 * jwt 검증
	 * 
	 * @param 	request			HttpServletRequest 객체
	 * @return	ResponseEntity(Http 상태값, body 값)
	 */
    @PostMapping("/jwt")
    public ResponseEntity<?> selectJwt(HttpServletRequest request) {
    	Map<String, Object> jwtMap = new HashMap<String, Object>();
    	jwtMap = jwtProvider.validateJwt(jwtProvider.getHeaderJwt(request.getHeader("Authorization")));
    	boolean checkYN = (boolean) jwtMap.get("checkYN");
        jwtMap.remove("checkYN");

        if (!checkYN) {
        	if (jwtMap.get("e1") != null) {
        		jwtMap.put("e1", jwtMap.get("e1"));    		
        	} else if (jwtMap.get("e2") != null) {
        		jwtMap.put("e2", jwtMap.get("e2"));
        	} else if (jwtMap.get("e3") != null) {
        		jwtMap.put("e3", jwtMap.get("e3"));
        	} else if (jwtMap.get("e4") != null) {
        		jwtMap.put("e4", jwtMap.get("e4"));
        	} else if (jwtMap.get("e5") != null) {
        		jwtMap.put("e5", jwtMap.get("e5"));
        	}
        	return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(jwtMap);
        }

        if (jwtMap.get("e0") != null) {
    		jwtMap.put("e0", jwtMap.get("e0"));
    	}

        return ResponseEntity.status(HttpStatus.OK).body(jwtMap);
    }

}
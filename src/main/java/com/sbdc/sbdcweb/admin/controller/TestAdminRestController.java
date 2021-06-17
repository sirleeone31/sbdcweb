package com.sbdc.sbdcweb.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAdminRestController {

	@GetMapping("/admin/api/auth/test/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "Success User Contents!";
    }

    @GetMapping("/admin/api/auth/test/admin")
    @PreAuthorize("hasRole('SYSADMIN')")
    public String adminAccess() {
    	return "Success Admin Contents";
    }
}
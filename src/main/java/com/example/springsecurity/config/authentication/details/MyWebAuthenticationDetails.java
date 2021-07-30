package com.example.springsecurity.config.authentication.details;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean isPassed;

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String code = request.getParameter("code");
        String verifyCode = (String) request.getSession().getAttribute("verity_code");
        if (code == null || verifyCode == null || !code.equals(verifyCode)) {
            isPassed = true;
        }
    }

    public boolean isPassed() {
        return isPassed;
    }
}

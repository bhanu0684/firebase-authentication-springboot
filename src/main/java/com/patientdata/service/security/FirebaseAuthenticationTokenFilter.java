package com.patientdata.service.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.patientdata.service.security.model.FirebaseAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.google.api.client.util.Strings;


public class FirebaseAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final static String TOKEN_HEADER = "X-Firebase-Auth";

    public FirebaseAuthenticationTokenFilter() {
        super("/api/**");
        //super("*");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final String authToken = request.getHeader(TOKEN_HEADER);
        if (Strings.isNullOrEmpty(authToken)) {
            //throw new RuntimeException("Invaild auth token");
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return getAuthenticationManager().authenticate(new FirebaseAuthenticationToken(authToken));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);


        chain.doFilter(request, response);
    }
}
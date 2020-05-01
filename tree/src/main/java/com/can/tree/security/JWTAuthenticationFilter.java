package com.can.tree.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.can.tree.model.ResponseObject;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.can.tree.security.SecurityConstants.EXPIRATION_TIME;
import static com.can.tree.security.SecurityConstants.SECRET;
import static com.can.tree.security.SecurityConstants.HEADER_STRING;
import static com.can.tree.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
        	com.can.tree.model.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.can.tree.model.User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                //.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.setStatus(HttpServletResponse.SC_OK);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//        PrintWriter out = res.getWriter();
//        res.setContentType("application/json");
//        res.setCharacterEncoding("UTF-8");
//        out.print(HEADER_STRING+" "+ TOKEN_PREFIX + token);
//        out.flush();   
    }
    
    @Override 
    protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
   	 ResponseObject resObj = new ResponseObject();
   	 resObj.setStatus(ResponseObject.BAD_REQUEST);
   	 resObj.setMsg("Hatalı kullanıcı adı veya şifre");
   	 response.getOutputStream().write(resObj.toString().getBytes());
    }
    
}
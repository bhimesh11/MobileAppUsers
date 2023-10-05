package com.photoApp.dev.photoAppUsers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoApp.dev.photoAppUsers.common.userDto;
import com.photoApp.dev.photoAppUsers.model.userLoginModel;
import com.photoApp.dev.photoAppUsers.service.userServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;

public class  AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{


    private userServices userService;

    private Environment environment;

    public AuthenticationFilter(userServices usersService, Environment environment,
                                AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.userService = usersService;
        this.environment = environment;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
    {
        try{
            userLoginModel creds = new ObjectMapper().readValue(req.getInputStream(),userLoginModel.class);
            System.out.println(creds);
            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),
                    creds.getPassword(),
                    new ArrayList<>()));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req, 
	HttpServletResponse res, 
	FilterChain fc,
	Authentication auth) throws IOException, ServletException
    {
        Instant now  =  Instant.now();
       String userName =  ((User)auth.getPrincipal()).getUsername();
        System.out.println(userName);

       userDto userDetails = userService.getUserDetailsByEmail(userName);

       String tokensecret = environment.getProperty("token.secret");

   byte[] secretBytes = Base64.getEncoder().encode(tokensecret.getBytes());

    SecretKey secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());
        System.out.println(secretKey.toString());

       String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
                .setIssuedAt(Date.from(now))
                .signWith(secretKey,SignatureAlgorithm.HS512)
                .compact();
        System.out.println(token);

       res.addHeader("token",token);
       res.addHeader("userId",userDetails.getUserId());
        System.out.println(res.toString());

    }
}

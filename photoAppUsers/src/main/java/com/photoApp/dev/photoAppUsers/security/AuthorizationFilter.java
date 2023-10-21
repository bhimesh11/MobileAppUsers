package com.photoApp.dev.photoAppUsers.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter
{


    private  Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               Environment environment)
    {
        super(authenticationManager);
        this.environment = environment;
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws IOException, ServletException
    {
        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        if(authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix")))
        {
            chain.doFilter(req,res);
            return;
        }
     //   UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(req);

     //   SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(req,res);
    }
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req)
//    {
//        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));
//
//        if (authorizationHeader == null) {
//            return null;
//        }
//        String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");
//        String tokenSecret = environment.getProperty("token.secret");
//
//        if(tokenSecret==null)
//            return null;
//
//     Jwt
//
//        if (userId == null) {
//            return null;
//        }
//
//        return new UsernamePasswordAuthenticationToken(userId, null, jwtClaimsParser.getUserAuthorities());
//
//    }


}

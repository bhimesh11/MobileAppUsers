package com.photoApp.dev.apigateway.filters;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component // create instance and place it in a Application context
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>
{
    public AuthorizationHeaderFilter()
    {
        super(Config.class);
    }
    @Autowired
    private Environment environment;

    //accept the config object which we can use to access configuration properties and if needed customize
    @Override
    public GatewayFilter apply(Config config) {



        return (exchange, chain) ->
        {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
            {
                return Onerror(exchange,"No authorization Header available", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            //String jwt = authorizationHeader.replace("Bearer","");
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt))
            {
                return Onerror(exchange,"JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);

        };

    }

    private boolean isJwtValid(String jwt) {

        boolean returnValue = true;
        String subject = null;
        String tokenSecret = environment.getProperty("token.secret");

        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();

        try
        {
            Jwt<Header, Claims> parsedToken = jwtParser.parse(jwt); // we will get claims from here

             subject = parsedToken.getBody().getSubject();

        }
        catch (Exception ex)
        {
            returnValue = false;
        }
        if(subject ==null || subject.isEmpty())
        {
            returnValue =false;
        }
        return returnValue;
    }

    private Mono<Void> Onerror(ServerWebExchange exchange, String noAuthorizationHeaderAvailable, HttpStatus unauthorized) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);

        return response.setComplete();
    }

    public static class Config
    {
      //put configuration properties here
    }
}

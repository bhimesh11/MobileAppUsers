package com.appDev.JWTauthorities;

import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class jwtParser
{

    Jwt<?,?> jwtObject;

    public jwtParser(String jwt , String SecretToken)
    {
        this.jwtObject = parseJwt(jwt,SecretToken);

    }

    Jwt<?,?> parseJwt(String jwtString,String secretToken)
    {
        byte[] secretKeyBytes = Base64.getEncoder().encode(secretToken.getBytes());

        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        JwtParser jwtparser = Jwts.parser().setSigningKey(secretKey).build();

        return jwtparser.parse(jwtString);
    }

    public Collection<? extends GrantedAuthority> getUserAuthorities()
    {
        Collection<Map<String,String>> scopes  = ((Claims)jwtObject.getBody()).get("scope", List.class);

        return scopes.stream()
                .map((scopeMap) ->
                        new SimpleGrantedAuthority(
                                scopeMap.get("authority")))
                .collect(Collectors.toList());
    }

    public String getJwtSubject() {
      return  ((Claims)jwtObject.getBody()).getSubject();
    }
}

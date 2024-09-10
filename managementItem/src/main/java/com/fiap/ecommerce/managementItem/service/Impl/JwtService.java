package com.fiap.ecommerce.managementItem.service.Impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        System.out.println("secret"+secret);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_00))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }


}

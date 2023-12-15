package com.monarca.backendmonarca.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static String SECRET_KEY = "m0n4rc4_w3b";
    private static Algorithm ALGORTIHM = Algorithm.HMAC256(SECRET_KEY);
    public String create(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer("monarca_web")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORTIHM);

    }

    public boolean isValid(String jwt){
        try {
            JWT.require((ALGORTIHM)).build().verify(jwt);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public String getUsername(String jwt){
        return JWT.require(ALGORTIHM).build().verify(jwt).getSubject();
    }

}

package pl.nqriver.homebudget.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JWTService {

    private final Long millisecondsInDay = TimeUnit.DAYS.toMillis(1);
    private final String secret = "mySecret";

    public String generateJWTToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + millisecondsInDay))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}

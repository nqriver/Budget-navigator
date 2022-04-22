package pl.nqriver.homebudget.service;

import io.jsonwebtoken.Claims;
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

    private final Long MILLISECONDS_IN_DAY = TimeUnit.DAYS.toMillis(1);
    private final String SECRET = "mySecret";

    public String extractUserName(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public Date extractExpirationDate(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        var userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }


    public String generateJWTToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + MILLISECONDS_IN_DAY))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

}

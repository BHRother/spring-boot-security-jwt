package nl.palmapps.myawesomeproject.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.palmapps.myawesomeproject.security.transfer.JwtUserDto;
import org.joda.time.DateTime;

public class JwtTokenGenerator {

    public static String generateToken(JwtUserDto u, String secret) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("role", u.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(DateTime.now().plusSeconds(30).toDate())
                .compact();
    }
}

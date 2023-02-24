package raf.sk.carservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JwtUtils {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    public Claims getTokenClaims(String token) {

        byte[] decodedSecret = Base64.getDecoder().decode(jwtSecret.getBytes());

        try {
            return Jwts.parser()
                    .setSigningKey(decodedSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

package leets.enhance.global.jwt;



import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;

    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims parseClaims(String accessToken) {
        try {
            JwtParser jwtsParser = Jwts.parser().setSigningKey(secretKey);
            return jwtsParser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public void validateToken(String token) {
        try {
            parseClaims(token);
        } catch (UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public Authentication getAuthentication(String token) {
        return new PreAuthenticatedAuthenticationToken(this.getEmail(token),null, null);
    }


}



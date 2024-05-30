package leets.enhance.configuration.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JwtProvider {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private int expiration;

  public String createToken(String email, String name) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", email);
    claims.put("name", name);

    Date expirationDate = new Date(System.currentTimeMillis() + expiration);

    return Jwts.builder()
        .setSubject(email)
        .setClaims(claims)
        .setExpiration(expirationDate)
        .signWith(getSignInKey())
        .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String getEmailFromToken(String token) {
    if (token == null || token.isEmpty()) {
      return null;
    }
    String email = null;
    try {
      email = parseToken(token).getSubject();
    } catch (Exception e) {
      log.info("[JwtProvider_getEmailFromToken]{}", e.getMessage());
      return null;
    }
    return email;
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

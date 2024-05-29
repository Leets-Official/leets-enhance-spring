package leets.attendance.global.jwt;

import io.jsonwebtoken.*;
import leets.attendance.domain.user.domain.User;
import leets.attendance.global.jwt.exception.ExpiredTokenException;
import leets.attendance.global.jwt.exception.InvalidTokenException;
import leets.attendance.global.jwt.exception.NotFoundTokenRoleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        return makeToken(new Date(new Date().getTime() + Duration.ofDays(1).toMillis()), user);
    }

    public String generateRefreshToken(User user){
        return makeToken(new Date(new Date().getTime() + Duration.ofDays(6).toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(user.getName())
                .claim("ROLE","ROLE_USER")
                .claim("id", user.getUserId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //토큰 유효성 검사
    public boolean validToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        switch (claims.get("ROLE").toString()) {
            case "ROLE_USER" -> {
                return getUserAuthentication(token);
            }
            case "ROLE_ADMIN" -> {
                return getAdminAuthentication(token);
            }
            default -> {
                throw new NotFoundTokenRoleException();
            }
        }
    }

    public Authentication getUserAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    public Authentication getAdminAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}

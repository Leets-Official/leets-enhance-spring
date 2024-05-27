package leets.enhance.gloal.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenProvider tokenProvider;

    public String extractEmailFromToken(String authorizationHeader) {
        // authorization 헤더가 null이거나 "Bearer "로 시작하지 않는 경우 체크
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header."); // 유효하지 않은 Authorization 헤더입니다.
        }

        // authorization 헤더에서 "Bearer " 접두사를 제거
        String token = authorizationHeader.substring(7);

        // 토큰이 null이거나 정확히 2개의 점(.) 문자를 포함하지 않는 경우 체크
        if (token == null || token.chars().filter(ch -> ch == '.').count() != 2) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token."); // 유효하지 않은 JWT 토큰입니다.
        }

        // 토큰에서 Authentication 객체를 가져옴
        Authentication authentication = tokenProvider.getAuthentication(token);

        // 이메일 반환
        return authentication.getName();
    }
}

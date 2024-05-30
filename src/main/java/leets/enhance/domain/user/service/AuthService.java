package leets.enhance.domain.user.service;

import jakarta.transaction.Transactional;
import leets.enhance.domain.user.dto.DuplicateUserIdRequest;
import leets.enhance.gloal.jwt.TokenProvider;
import leets.enhance.domain.user.domain.User;
import leets.enhance.gloal.jwt.dto.Token;
import leets.enhance.domain.user.dto.UserLoginRequest;
import leets.enhance.domain.user.dto.UserRegisterRequest;
import leets.enhance.domain.user.dto.UserResponse;
import leets.enhance.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserResponse signup(UserRegisterRequest userRegisterRequest) {
        User user = userRegisterRequest.toUser(passwordEncoder);
        log.info("User {} registered", userRegisterRequest.name());
        return UserResponse.of(userRepository.save(user));
    }

    public Token login(UserLoginRequest userLoginRequest) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 토큰 발급
        return tokenProvider.generateToken(authentication);
    }

    public Boolean checkDuplicateId(DuplicateUserIdRequest duplicateUserIdRequest) {
        return userRepository.existsByUsername(duplicateUserIdRequest.email());
    }
}

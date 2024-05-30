package leets.enhance.service;


import leets.enhance.domain.User;
import leets.enhance.dto.TokenResponseDto;
import leets.enhance.dto.UserRequestDto;
import leets.enhance.exception.CustomException;
import leets.enhance.exception.ErrorCode;
import leets.enhance.jwt.JwtUtil;
import leets.enhance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponseDto login(UserRequestDto.Login requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.createToken(user.getEmail(), "ACCESS_TOKEN");
        String refreshToken = jwtUtil.createToken(user.getEmail(), "REFRESH_TOKEN");

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

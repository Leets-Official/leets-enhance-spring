package leets.enhance.domain.user.application;


import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.exception.InvalidPasswordException;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.domain.user.presentation.dto.Request.LoginRequestDto;
import leets.enhance.domain.user.presentation.dto.Request.RegisterRequestDto;
import leets.enhance.domain.user.presentation.dto.Response.DuplicationResponseDto;
import leets.enhance.domain.user.presentation.dto.Response.TokenResponseDto;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public User register(RegisterRequestDto registerRequestDto){
        User user = User.create(registerRequestDto, passwordEncoder);
        return userRepository.save(user);
    }

    public DuplicationResponseDto checkDuplicateId(String email){
        boolean isDuplicate = userRepository.existsByEmail(email);
        return new DuplicationResponseDto(isDuplicate);
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String accessToken = tokenProvider.generateToken(user);
        return new TokenResponseDto(accessToken);
    }

}

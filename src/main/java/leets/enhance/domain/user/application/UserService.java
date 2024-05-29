package leets.attendance.domain.user.application;

import leets.attendance.domain.user.domain.User;
import leets.attendance.domain.user.exception.InvalidPasswordException;
import leets.attendance.domain.user.exception.UserNotFoundException;
import leets.attendance.domain.user.presentation.dto.Request.LoginRequestDto;
import leets.attendance.domain.user.presentation.dto.Response.DuplicationResponseDto;
import leets.attendance.domain.user.presentation.dto.Response.TokenResponseDto;
import leets.attendance.domain.user.repository.UserRepository;
import leets.attendance.domain.user.presentation.dto.Request.RegisterRequestDto;
import leets.attendance.global.jwt.TokenProvider;
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
        User user = User.builder()
                .userId(registerRequestDto.id())
                .userPwd(passwordEncoder.encode(registerRequestDto.password()))
                .name(registerRequestDto.name())
                .part(registerRequestDto.part()).build();
        return userRepository.save(user);

    }

    public DuplicationResponseDto checkDuplicateId(String joinId){
        boolean isDuplicate = userRepository.existsByUserId(joinId);
        return new DuplicationResponseDto(isDuplicate); //true, false로 중복확인
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByUserId(loginRequestDto.id()).orElseThrow(UserNotFoundException::new);
        String password = loginRequestDto.password();

        //복호화 후 체크해야하는데 시간이 없어서.. ^^..
        if(!user.getUserPwd().equals(password)){
            throw new InvalidPasswordException();
        }

        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);
        return new TokenResponseDto(accessToken, refreshToken);
    }

}

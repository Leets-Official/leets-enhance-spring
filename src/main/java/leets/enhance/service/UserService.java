package leets.enhance.service;



import leets.enhance.domain.User;
import leets.enhance.dto.UserRequestDto;
import leets.enhance.dto.UserResponseDto;
import leets.enhance.exception.CustomException;
import leets.enhance.exception.ErrorCode;
import leets.enhance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto register(UserRequestDto.Register requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .boosterCount(3)
                .build();

        userRepository.save(user);

        return UserResponseDto.from(user);
    }
}

package leets.enhance.domain.user.application;

import leets.enhance.domain.user.dao.UserRepository;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.dto.JoinDto;
import leets.enhance.domain.user.dto.ResponseDto;
import leets.enhance.global.CustomException;
import leets.enhance.global.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    public String checkDuplicateId(String email) {
        if(userRepository.existsByEmail(email)){
            return "이미 존재하는 이메일입니다!";
        }
        return "사용 가능한 이메일입니다!";
    }

    public ResponseDto register(JoinDto joinDto) {

        if(userRepository.existsByEmail(joinDto.getEmail())){
            throw new CustomException("이미 존재하는 이메일입니다!");
        }

        User user = User.builder()
                .email(joinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .name(joinDto.getName())
                .build();

        userRepository.save(user);

        return ResponseDto.build(user);

    }



}
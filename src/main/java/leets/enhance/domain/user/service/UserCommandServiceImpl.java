package leets.enhance.domain.user.service;

import leets.enhance.domain.user.DTO.UserDto;
import leets.enhance.domain.user.User;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.utils.JwtToken;
import leets.enhance.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtToken signIn(String id, String password) {
        System.out.println("Attempting to authenticate user with id: " + id);

        // 유저 존재하는지 확인
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            System.out.println("User not found with id: " + id);
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOpt.get();

        System.out.println("Encoded password from DB: " + user.getPassword());
        System.out.println("Raw password input: " + password);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password matches: " + matches);
        if (!matches) {
            throw new BadCredentialsException("Invalid credentials");
        }


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        System.out.println("authenticationToken 발급 완" + authenticationToken);

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            System.out.println("Authentication successful for user: " + id);
            return jwtToken;
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed for user: " + id);
            throw e;
        }
    }



    @Transactional
    @Override
    public UserDto signUp(UserDto.SignUpDto signUpDto) {
        if (userRepository.existsById(signUpDto.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }
}

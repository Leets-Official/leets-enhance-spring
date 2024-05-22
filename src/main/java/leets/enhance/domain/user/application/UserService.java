package leets.enhance.domain.user.application;


import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.dto.LoginRequest;
import leets.enhance.domain.user.dto.LoginResponse;
import leets.enhance.domain.user.dto.SignUpRequest;
import leets.enhance.domain.user.dto.SignUpResponse;;
import leets.enhance.domain.user.exception.ConflictIdException;
import leets.enhance.domain.user.exception.InvalidIdException;
import leets.enhance.domain.user.exception.InvalidPasswordException;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public SignUpResponse register(SignUpRequest userRequest) throws Exception{
        String email = userRequest.email();
        if(userRepository.findByEmail(email).isPresent()) throw new ConflictIdException();
        User user = User.builder()
                .email(email)
                .nickname(userRequest.name())
                .password(userRequest.password())
                .build();
        userRepository.save(user);
        return SignUpResponse.of(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = checkValidUser(loginRequest);
        return LoginResponse.from(user,tokenProvider.createToken(user.getEmail()));
    }

    public User checkValidUser(LoginRequest loginRequest) {
        if (!(userRepository.findByEmail(loginRequest.email()).isPresent())) {
            throw new InvalidIdException();
        } else if (!(userRepository.findByPassword(loginRequest.password())).isPresent()) {
            throw new InvalidPasswordException();
        }
        return userRepository.findByEmail(loginRequest.email()).get();
    }

    public String checkDuplicateId(String email) throws Exception{
        if(userRepository.findByEmail(email).isPresent()){
            throw new ConflictIdException();
        }
        return email;
    }
}

package leets.enhance.domain.user.application;


import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.dto.SignUpRequest;
import leets.enhance.domain.user.dto.SignUpResponse;
import leets.enhance.domain.user.dto.UserRequest;
import leets.enhance.domain.user.dto.UserResponse;
import leets.enhance.domain.user.exception.ConflictIdException;
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
        if(userRepository.findById(email).isPresent()) throw new ConflictIdException();
        User user = User.builder()
                .email(email)
                .nickname(userRequest.name())
                .password(userRequest.password())
                .build();
        userRepository.save(user);
        return SignUpResponse.fromUser(user);
    }

    public String checkDuplicateId(String email) throws Exception{
        if(userRepository.findById(email).isPresent()){
            throw new ConflictIdException();
        }
        return email;
    }
}

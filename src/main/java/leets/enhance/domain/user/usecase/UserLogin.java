package leets.enhance.domain.user.usecase;

import leets.enhance.domain.shared.exception.PasswordNotMatchException;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.global.jwt.JwtProvider;
import leets.enhance.global.jwt.dto.JwtResponse;
import leets.enhance.global.jwt.exception.ExpiredTokenException;
import leets.enhance.global.jwt.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogin implements UserTokenService{
    private final JwtProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse execute(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        validatePassword(password, user);

        String accessToken = tokenProvider.generateAccessToken(user.getUid(), user.getUsername());
        String refreshToken = generateRefreshToken(user);

        return new JwtResponse(accessToken, refreshToken);
    }

    private void validatePassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException();
        }
    }

    private void updateUserRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public String generateRefreshToken(User user) {
        String refreshToken = user.getRefreshToken();
        if (refreshToken == null || !isValidToken(refreshToken)) {
            refreshToken = tokenProvider.generateRefreshToken(user.getUid(), user.getUsername());
            updateUserRefreshToken(user, refreshToken);
        }
        return refreshToken;
    }

    @Override
    public Boolean isValidToken(String token) {
        try {
            tokenProvider.validateToken(token);
            return true;
        } catch (InvalidTokenException | ExpiredTokenException e) {
            return false;
        }
    }
}

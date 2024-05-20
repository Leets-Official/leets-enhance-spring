package leets.enhance.domain.user.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.status.Level;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.dto.response.RegisterResponse;
import leets.enhance.global.jwt.JwtProvider;
import leets.enhance.global.jwt.exception.ExpiredTokenException;
import leets.enhance.global.jwt.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegister implements UserTokenService{
    private final JwtProvider tokenProvider;
    private final UserRepository userRepository;
    private final BladeRepository bladeRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse execute(String username, String password, String bladeName) {
        User user = User.create(username, passwordEncoder.encode(password), 3);
        userRepository.save(user);

        Blade blade = Blade.create(bladeName, Level.LV1, user);
        bladeRepository.save(blade);

        String refreshToken = generateRefreshToken(user);
        user.updateUser(blade, refreshToken);

        userRepository.save(user);
        return new RegisterResponse(true, "가입 성공");
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

    private void updateUserRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public Boolean isUsernameDuplicated(String username) {
        return userRepository.existsUserByUsername(username);
    }
}

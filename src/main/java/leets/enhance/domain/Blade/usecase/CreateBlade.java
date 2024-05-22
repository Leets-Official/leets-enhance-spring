package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.dto.response.RegisterResponse;
import leets.enhance.domain.Blade.status.Level;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBlade {
    private final BladeRepository bladeRepository;
    private final UserRepository userRepository;

    public RegisterResponse execute(String bladeName, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Blade blade = Blade.create(bladeName, Level.LV1, user);
        bladeRepository.save(blade);

        user.createBlade(blade);
        userRepository.save(user);
        return new RegisterResponse(true, "무기 생성 성공");
    }
}

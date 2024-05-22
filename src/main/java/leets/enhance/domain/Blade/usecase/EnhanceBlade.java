package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.dto.response.EnhanceResponse;
import leets.enhance.domain.Blade.status.Level;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnhanceBlade {
    private final UserRepository userRepository;
    private final Random random = new Random();
    private final EnhanceSuccess enhanceSuccess;
    private final EnhanceFailWithoutBreak enhanceFailWithoutBreak;
    private final EnhanceFailWithBreak enhanceFailWithBreak;

    public EnhanceResponse execute(String username, Boolean useCoupon) {
        Blade blade = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getBlade();

        return tryEnhance(blade, useCoupon);
    }

    private EnhanceResponse tryEnhance(Blade blade, Boolean useCoupon) {
        Level level = blade.getLevel();
        Double successProbability = level.getSuccessProbability();
        if (useCoupon) {
            successProbability += 0.05;
            handleCouponRemaining();
        }

        if (random.nextDouble() <= successProbability) {
            return enhanceSuccess.handle(blade, level);
        }
        if (random.nextDouble() <= level.getBreakProbability()) {
            return enhanceFailWithBreak.handle(blade, level);
        }
        return enhanceFailWithoutBreak.handle(blade, level);
    }

    private void handleCouponRemaining() {
    }
}

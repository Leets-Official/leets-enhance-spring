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
    private final BladeRepository bladeRepository;

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

        if (random.nextDouble() <= successProbability) {// 무기 강화
            return handleSuccess(blade, level);
        }
        if (random.nextDouble() <= level.getBreakProbability()) { // 파괴 성공
            return handleFailWithBreak(blade);
        }
        return handleFailWithoutBreak(blade, level);
    }

    private void handleCouponRemaining() {
    }

    private EnhanceResponse handleFailWithoutBreak(Blade blade, Level level) {
        Level newLevel = Level.fromLevel(level.getLevel() - 1);
        blade.updateLevel(newLevel);
        return new EnhanceResponse(false, false);
    }

    private EnhanceResponse handleFailWithBreak(Blade blade) {
        Level newLevel = Level.fromLevel(0);
        blade.updateLevel(newLevel);
        return new EnhanceResponse(false, true);
    }

    private EnhanceResponse handleSuccess(Blade blade, Level level) {
        Level newLevel = Level.fromLevel(level.getLevel() + 1);
        blade.updateLevel(newLevel);
        bladeRepository.save(blade);
        return new EnhanceResponse(true, false);
    }
}

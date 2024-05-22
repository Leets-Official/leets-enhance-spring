package leets.enhance.domain.Blade.usecase;

import jakarta.transaction.Transactional;
import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.dto.response.EnhanceResponse;
import leets.enhance.domain.Blade.status.Level;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EnhanceBlade {
    private static final double COUPON_SUCCESS_INCREMENT = 0.05;

    private final UserRepository userRepository;
    private final Random random = new Random();
    private final EnhanceSuccess enhanceSuccess;
    private final EnhanceFailWithoutBreak enhanceFailWithoutBreak;
    private final EnhanceFailWithBreak enhanceFailWithBreak;

    public EnhanceResponse execute(String username, Boolean useCoupon) {
        Blade blade = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getBlade();

        return tryEnhance(username, blade, useCoupon);
    }

    private EnhanceResponse tryEnhance(String username, Blade blade, Boolean useCoupon) {
        Level level = blade.getLevel();
        Double successProbability = level.getSuccessProbability();

        if (useCoupon) {
            successProbability = handleCouponRemaining(username, successProbability);
        }
        if (random.nextDouble() <= successProbability) {
            return enhanceSuccess.handle(blade, level);
        }
        if (random.nextDouble() <= level.getBreakProbability()) {
            return enhanceFailWithBreak.handle(blade, level);
        }
        return enhanceFailWithoutBreak.handle(blade, level);
    }

    private double handleCouponRemaining(String username, double successProbability) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (user.getUpgradeCouponRemaining() > 0) {
            user.updateCouponRemaining(user.getUpgradeCouponRemaining() - 1);
            successProbability += COUPON_SUCCESS_INCREMENT;
        }
        return successProbability;
    }}

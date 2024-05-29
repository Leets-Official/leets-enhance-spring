package leets.enhance.domain.blade.service;

import leets.enhance.domain.blade.domain.Blade;
import leets.enhance.domain.blade.domain.BladeLevel;
import leets.enhance.domain.blade.domain.repository.BladeRepository;
import leets.enhance.domain.blade.dto.*;
import leets.enhance.domain.user.exception.NoCouponException;
import leets.enhance.domain.blade.exception.TooLongBladeNameException;
import leets.enhance.domain.blade.exception.BladeNotFoundException;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.gloal.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BladeService {
    private final BladeRepository bladeRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public BladeCreateResponse createBlade(String authorizationHeader, BladeCreateRequest bladeCreateRequest) {
        String userEmail = jwtService.extractEmailFromToken(authorizationHeader);
        User user = userRepository.findByUsername(userEmail).orElseThrow(UserNotFoundException::new);

        if (bladeCreateRequest.name().length() > 5) {
            throw new TooLongBladeNameException();
        }

        Blade blade = Blade.builder()
                .name(bladeCreateRequest.name())
                .level(BladeLevel.fromLevel(1))
                .user(user)
                .build();

        user.createBlade(blade);

        return BladeCreateResponse.of(bladeRepository.save(blade), blade.getUser());
    }

    public GetMyBladeResponse getMyBlade(String authorizationHeader) {
        String userEmail = jwtService.extractEmailFromToken(authorizationHeader);
        User user = userRepository.findByUsername(userEmail).orElseThrow(UserNotFoundException::new);
        Blade blade = bladeRepository.findByUser(user).orElseThrow(BladeNotFoundException::new);

        return GetMyBladeResponse.of(blade, user);
    }

    @Transactional
    public BladeEnhanceResponse enhance(String authorizationHeader, BladeEnhanceRequest bladeEnhanceRequest) {
        String userEmail = jwtService.extractEmailFromToken(authorizationHeader);
        User user = userRepository.findByUsername(userEmail).orElseThrow(UserNotFoundException::new);
        Blade blade = bladeRepository.findByUser(user).orElseThrow(BladeNotFoundException::new);
        double increaseProbability = 0;

        if (bladeEnhanceRequest.UseCoupon()) {
            if (user.getUpgradeCouponRemaining() == 0) {
                throw new NoCouponException();
            }

            user.useUpgradeCoupon();
            increaseProbability = 0.1;
        }

        BladeEnhanceResponse bladeEnhanceResponse;

        if (Math.random() <= blade.getLevel().getSuccessProbability() + increaseProbability) {
            blade.updateLevel(BladeLevel.fromLevel(blade.getLevel().getLevel() + 1));
            bladeEnhanceResponse = BladeEnhanceResponse.of(blade, "강화에 성공했습니다.");
        }

        if (Math.random() <= blade.getLevel().getDestroyProbability()) {
            blade.updateLevel(BladeLevel.fromLevel(0));
            bladeEnhanceResponse = BladeEnhanceResponse.of(blade, "검이 파괴되었습니다.");
        } else {
            blade.updateLevel(BladeLevel.fromLevel(blade.getLevel().getLevel() - 1));
            bladeEnhanceResponse = BladeEnhanceResponse.of(blade, "강화에 실패했습니다.");
        }


        bladeRepository.save(blade);
        return bladeEnhanceResponse;
    }

    public List<Top10BladeResponse> getTop10Blade() {;
        return bladeRepository.findTop10ByOrderByLevelDesc().stream()
                .map(Top10BladeResponse::of)
                .collect(Collectors.toList());
    }
}
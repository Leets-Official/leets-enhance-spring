package leets.enhance.domain.blade.service;

import leets.enhance.domain.blade.domain.Blade;
import leets.enhance.domain.blade.domain.BladeLevel;
import leets.enhance.domain.blade.domain.repository.BladeRepository;
import leets.enhance.domain.blade.dto.BladeCreateRequest;
import leets.enhance.domain.blade.dto.BladeCreateResponse;
import leets.enhance.domain.blade.dto.GetMyBladeResponse;
import leets.enhance.domain.blade.dto.Top10BladeResponse;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
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

        if (bladeCreateRequest.name().length() > 5) {
            throw new IllegalStateException("이름은 5자 이하로 입력해주세요.");
        }

        Blade blade = Blade.builder()
                .name(bladeCreateRequest.name())
                .level(BladeLevel.fromLevel(1))
                .user(userRepository.findByUsername(userEmail))
                .build();

        return BladeCreateResponse.of(bladeRepository.save(blade), blade.getUser());
    }

    public GetMyBladeResponse getMyBlade(String authorizationHeader) {
        String userEmail = jwtService.extractEmailFromToken(authorizationHeader);
        User user = userRepository.findByUsername(userEmail);
        Blade blade = bladeRepository.findByUser(user).orElseThrow(IllegalStateException::new);

        return GetMyBladeResponse.of(blade, user);
    }

    @Transactional
    public String enhance(String authorizationHeader) {
        String userEmail = jwtService.extractEmailFromToken(authorizationHeader);
        User user = userRepository.findByUsername(userEmail);
        Blade blade = bladeRepository.findByUser(user).orElseThrow(IllegalStateException::new);

        if (Math.random() <= blade.getLevel().getSuccessProbability()) {
            blade.updateLevel(BladeLevel.fromLevel(blade.getLevel().getLevel() + 1));
            bladeRepository.save(blade);
            return "강화에 성공했습니다.";
        }

        if (Math.random() <= blade.getLevel().getDestroyProbability()) {
            blade.updateLevel(BladeLevel.fromLevel(0));
            bladeRepository.save(blade);
            return "검이 파괴되었습니다.";
        }

        blade.updateLevel(BladeLevel.fromLevel(blade.getLevel().getLevel() - 1));
        bladeRepository.save(blade);
        return "강화에 실패했습니다.";
    }

    public List<Top10BladeResponse> getTop10Blade() {
        List<Blade> top10Blade = bladeRepository.findTop10ByOrderByLevelDesc();
        return top10Blade.stream()
                .map(Top10BladeResponse::of)
                .collect(Collectors.toList());
    }
}

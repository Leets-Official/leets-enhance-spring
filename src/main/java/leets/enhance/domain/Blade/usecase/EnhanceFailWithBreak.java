package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.dto.response.EnhanceResponse;
import leets.enhance.domain.Blade.status.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnhanceFailWithBreak implements EnhanceResultHandler{
    private final BladeRepository bladeRepository;

    @Override
    public EnhanceResponse handle(Blade blade, Level level) {
        blade.updateLevel(Level.fromLevel(0));
        bladeRepository.save(blade);
        return new EnhanceResponse(false, true, blade.getLevel());
    }
}

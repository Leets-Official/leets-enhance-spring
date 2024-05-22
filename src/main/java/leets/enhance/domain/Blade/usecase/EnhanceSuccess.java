package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.dto.response.EnhanceResponse;
import leets.enhance.domain.Blade.status.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnhanceSuccess implements EnhanceResultHandler{
    private final BladeRepository bladeRepository;


    @Override
    public EnhanceResponse handle(Blade blade, Level level) {
        Level newLevel = Level.fromLevel(level.getLevel() + 1);
        blade.updateLevel(newLevel);
        bladeRepository.save(blade);
        return new EnhanceResponse(true, false, blade.getLevel());
    }
}

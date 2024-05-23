package leets.enhance.domain.weapon.application;

import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.domain.weapon.Level;
import leets.enhance.domain.weapon.WeaponResponseMessage;
import leets.enhance.domain.weapon.domain.Weapon;
import leets.enhance.domain.weapon.dto.CreateWeaponRequest;
import leets.enhance.domain.weapon.dto.EnhanseResponse;
import leets.enhance.domain.weapon.exception.WeaponAlreadyExistException;
import leets.enhance.domain.weapon.exception.WeaponNotFoundException;
import leets.enhance.domain.weapon.repository.WeaponRepository;
import leets.enhance.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Random;
import static leets.enhance.domain.weapon.WeaponResponseMessage.*;

@Service
@RequiredArgsConstructor
public class WeaponService {
    private final WeaponRepository weaponRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private static final int ENHANCEMENT_FAILURE_RATE = 10;
    private static final int MAX_DESTROY_RATE = 50;

    public void createWeapon(Authentication authentication, CreateWeaponRequest createWeaponRequest) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if (weaponRepository.findByUser(user).isPresent()) {
            throw new WeaponAlreadyExistException();
        }
        weaponRepository.save(Weapon.createWeapon(user, createWeaponRequest.weaponName()));
    }

    public EnhanseResponse executeEnhance(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Weapon weapon = weaponRepository.findByUser(user).orElseThrow(WeaponNotFoundException::new);
        Random random = new Random();
        WeaponResponseMessage weaponResponseMessage = UPGRADE_FAILED;
        Integer level = weapon.getLevel();
        Level currentLevel = Level.getLevelByNumber(level).get();
        if (random.nextInt(100) < currentLevel.getSuccessRate()) {
            level = weapon.getLevel()+1;
            weaponResponseMessage = UPGRADE_SUCCESS;
        } else {
            if (random.nextInt(100) < Math.min(currentLevel.getDestroyRate(), MAX_DESTROY_RATE)) {
                weaponResponseMessage = LEVEL_DOWN;
                level = weapon.getLevel()-1;
                if (random.nextInt(100) < 50) {
                    weaponResponseMessage = DESTROYED;
                    level = 0;
                }
            }
        }
        weaponRepository.save(Weapon.updateWeapon(weapon, level));
        return new EnhanseResponse(weaponResponseMessage,level);
    }

}

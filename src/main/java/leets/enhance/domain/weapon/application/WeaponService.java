package leets.enhance.domain.weapon.application;

import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.domain.user.repository.UserRepository;
import leets.enhance.domain.weapon.Level;
import leets.enhance.domain.weapon.WeaponResponseMessage;
import leets.enhance.domain.weapon.domain.Weapon;
import leets.enhance.domain.weapon.dto.*;
import leets.enhance.domain.weapon.exception.NoItemException;
import leets.enhance.domain.weapon.exception.WeaponAlreadyExistException;
import leets.enhance.domain.weapon.exception.WeaponNotFoundException;
import leets.enhance.domain.weapon.repository.WeaponRepository;
import leets.enhance.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

import java.util.Random;

import static leets.enhance.domain.weapon.WeaponResponseMessage.*;

@Service
@RequiredArgsConstructor
public class WeaponService {
    private final WeaponRepository weaponRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private static final int MAX_DESTROY_RATE = 50;

    public void createWeapon(Authentication authentication, CreateWeaponRequest createWeaponRequest) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if (weaponRepository.findByUser(user).isPresent()) {
            throw new WeaponAlreadyExistException();
        }
        weaponRepository.save(Weapon.createWeapon(user, createWeaponRequest.weaponName()));
    }

    public EnhanceResponse executeEnhance(Authentication authentication, EnhanceRequest request) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Weapon weapon = weaponRepository.findByUser(user).orElseThrow(WeaponNotFoundException::new);
        Random random = new Random();
        Integer increase = ((isUsedIncreaseProbability(user,request.useIncreaseProbability()))? 30 : 0);
        WeaponResponseMessage weaponResponseMessage;
        Integer level = weapon.getLevel();
        Level currentLevel = getValidLevel(level);
        if (random.nextInt(100) < currentLevel.getSuccessRate()+increase) {
            level = weapon.getLevel() + 1;
            weaponResponseMessage = UPGRADE_SUCCESS;
        } else {
            weaponResponseMessage = UPGRADE_FAILED;
            if (random.nextInt(100) < Math.min(currentLevel.getDestroyRate(), MAX_DESTROY_RATE)) {
                weaponResponseMessage = LEVEL_DOWN;
                level = weapon.getLevel() - 1;
                if (random.nextInt(100) < 50) {
                    weaponResponseMessage = DESTROYED;
                    level = 0;
                }
            }
        }
        weaponRepository.save(Weapon.updateWeapon(weapon, level));
        return new EnhanceResponse(weaponResponseMessage, level);
    }


    public Boolean isUsedIncreaseProbability(User user, Boolean useIncreaseProbability) {
        if (useIncreaseProbability && user.getIncreasingProbability() <= 0) {
            throw new NoItemException();
        } else {
            userRepository.save(User.decreaseItem(user));
            return true;
        }
    }

    public GetItemsResponse getItems(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        Weapon weapon = weaponRepository.findByUser(user).orElseThrow(WeaponNotFoundException::new);
        return GetItemsResponse.from(user,weapon);
    }

    public List<GetItemsTopResponse> getItemsTop() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("level").descending());

        return weaponRepository.findAllByOrderByLevelDesc(pageable)
                .stream()
                .map(GetItemsTopResponse::of)
                .toList();
    }

    public Level getValidLevel(Integer level) {
        return (level > 7 ? Level.getLevelByNumber(7).get() : Level.getLevelByNumber(level).get());
    }

}

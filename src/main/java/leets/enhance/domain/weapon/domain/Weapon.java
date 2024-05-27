package leets.enhance.domain.weapon.domain;


import jakarta.persistence.*;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.weapon.Level;
import leets.enhance.domain.weapon.dto.EnhanceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

import static leets.enhance.domain.weapon.WeaponResponseMessage.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {

    private static final int RANDOM_BOUND = 100;
    private static final int MAX_DESTROY_RATE = 50;
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long weaponId;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String weaponName;


    public static Weapon createWeapon(User user,String weaponName) {
        return Weapon.builder()
                .user(user)
                .level(1)
                .weaponName(weaponName)
                .build();
    }

    public static Weapon updateWeapon(Weapon weapon,Integer level) {
        return Weapon.builder()
                .weaponId(weapon.getWeaponId())
                .user(weapon.getUser())
                .level(level)
                .weaponName(weapon.getWeaponName())
                .build();
    }

    public EnhanceResponse enhance(Integer increaseProbability) {
        Random random = new Random();
        Level currentLevel = Level.getLevelByNumber(this.level).get();

        if (random.nextInt(RANDOM_BOUND) < currentLevel.getSuccessRate() + increaseProbability) {
            this.level++;
            return new EnhanceResponse(UPGRADE_SUCCESS, this.level);
        } else {
            return handleFailure(random, currentLevel);
        }
    }

    private EnhanceResponse handleFailure(Random random, Level currentLevel) {
        if (random.nextInt(RANDOM_BOUND) < Math.min(currentLevel.getDestroyRate(), MAX_DESTROY_RATE)) {
            if (random.nextInt(RANDOM_BOUND) < 50) {
                this.level = 0;
                return new EnhanceResponse(DESTROYED, this.level);
            } else {
                this.level--;
                return new EnhanceResponse(LEVEL_DOWN, this.level);
            }
        } else {
            return new EnhanceResponse(UPGRADE_FAILED, this.level);}
    }


}

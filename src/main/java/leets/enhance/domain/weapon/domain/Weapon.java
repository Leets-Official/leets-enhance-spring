package leets.enhance.domain.weapon.domain;


import jakarta.persistence.*;
import leets.enhance.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {

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

}

package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
import leets.enhance.domain.Blade.domain.Blade;
import lombok.*;
import java.util.UUID;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer upgradeCouponRemaining;

    @OneToOne
    @JoinColumn(name = "blade_uid", columnDefinition = "BINARY(16)")
    private Blade blade;

    private String refreshToken;

    public static User create(String username, String password, Integer upgradeCouponRemaining) {
        return User.builder()
                .username(username)
                .password(password)
                .upgradeCouponRemaining(upgradeCouponRemaining)
                .build();
    }

    @Builder
    public User(String username, String password, Integer upgradeCouponRemaining) {
        this.username = username;
        this.password = password;
        this.upgradeCouponRemaining = upgradeCouponRemaining;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void createBlade(Blade blade) {
        this.blade = blade;
    }

    public void updateCouponRemaining(Integer upgradeCouponRemaining) {
        this.upgradeCouponRemaining = upgradeCouponRemaining;
    }
}

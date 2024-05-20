package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
import leets.enhance.domain.Blade.domain.Blade;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(columnDefinition = "char(13)", nullable = false)
    private String password;

    @ColumnDefault("3")
    private Integer upgradeCouponRemaining;

    @OneToOne
    @JoinColumn(name = "uid", columnDefinition = "BINARY(16)")
    private Blade blade;

    @Column(nullable = false)
    private String refreshToken;

    public static User create(String username, String password, Blade blade, String refreshToken) {
        return User.builder()
                .username(username)
                .password(password)
                .blade(blade)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public User(String username, String password, Blade blade, String refreshToken) {
        this.username = username;
        this.password = password;
        this.blade = blade;
        this.refreshToken = refreshToken;
    }
}

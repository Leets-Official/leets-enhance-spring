package leets.enhance.domain.Blade.domain;

import jakarta.persistence.*;
import leets.enhance.domain.Blade.status.Level;
import leets.enhance.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "blade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;

    @Column(columnDefinition = "char(5)", nullable = false)
    private String name;

    @Column(nullable = false)
    private Level level;

    @OneToOne
    @JoinColumn(name = "uid", columnDefinition = "BINARY(16)")
    private User user;

    public static Blade create(String name, Level level, User user) {
        return Blade.builder()
                .name(name)
                .level(level)
                .user(user)
                .build();
    }

    @Builder
    public Blade(String name, Level level, User user) {
        this.name = name;
        this.level = level;
        this.user = user;
    }

    public void updateLevel(Level level) {
        this.level = level;
    }
}

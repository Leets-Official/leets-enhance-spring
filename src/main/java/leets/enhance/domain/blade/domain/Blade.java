package leets.enhance.domain.blade.domain;

import jakarta.persistence.*;
import leets.enhance.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class Blade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false)
    private BladeLevel level;

    @OneToOne(mappedBy = "blade")
    private User user;

    public void updateLevel(BladeLevel level) {
        this.level = level;
    }
}
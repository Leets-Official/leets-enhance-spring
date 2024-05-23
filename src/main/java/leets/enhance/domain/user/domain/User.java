package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
import leets.enhance.domain.weapon.domain.Weapon;
import lombok.*;

import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer increasingProbability;
}

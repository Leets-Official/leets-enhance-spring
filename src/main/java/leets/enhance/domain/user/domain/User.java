package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
import leets.enhance.domain.common.BaseTimeEntity;
import leets.enhance.domain.user.presentation.dto.Request.RegisterRequestDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    public static User create(final RegisterRequestDto dto, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(dto.email())
                .name(dto.name())
                .password(passwordEncoder.encode(dto.password()))
                .build();
    }

}

package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
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

    @Column(columnDefinition = "char(13)", nullable = false)
    private String password;

    @Column(nullable = false)
    private String refreshToken;

    public static User create(String username, String password, String refreshToken) {
        return User.builder()
                .username(username)
                .password(password)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public User(String username, String password, String refreshToken) {
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
    }
}

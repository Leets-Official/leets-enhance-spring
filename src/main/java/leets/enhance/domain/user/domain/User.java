package leets.attendance.domain.user.domain;

import jakarta.persistence.*;
import leets.attendance.domain.common.BaseTimeEntity;
import lombok.*;

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
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPwd;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;
}

package leets.enhance.domain.item;

import jakarta.persistence.*;
import leets.enhance.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private int level = 1;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

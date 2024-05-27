package leets.enhance.domain.item.domain;

import jakarta.persistence.*;
import leets.enhance.domain.user.domain.User;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;
    private Integer ticket; //남은 확률 증가권 개수

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

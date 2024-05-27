package leets.enhance.domain.user.domain;

import jakarta.persistence.*;
import leets.enhance.domain.item.domain.Item;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String pwd;
    private String name;
    @OneToOne(mappedBy = "user")
    private Item item;
}

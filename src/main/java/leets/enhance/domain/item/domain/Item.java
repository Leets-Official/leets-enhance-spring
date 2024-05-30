package leets.enhance.domain.item.domain;

import jakarta.persistence.*;
import leets.enhance.domain.item.presentation.dto.request.CreateItemRequest;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.presentation.dto.Request.RegisterRequestDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String name;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    public static Item create(final CreateItemRequest createItemRequest, final User user){
        return Item.builder()
                .name(createItemRequest.itemName())
                .level(1)
                .user(user).build();
    }
}
